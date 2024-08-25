package com.tuchanski.EasyLib.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tuchanski.EasyLib.models.Book;
import com.tuchanski.EasyLib.models.Library;
import com.tuchanski.EasyLib.models.User;
import com.tuchanski.EasyLib.models.DTOs.LibraryRecordDTO;
import com.tuchanski.EasyLib.repositories.BookRepository;
import com.tuchanski.EasyLib.repositories.LibraryRepository;
import com.tuchanski.EasyLib.repositories.UserRepository;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.libraryRepository.findAll());
    }

    public ResponseEntity<Object> getLibraryById(UUID libraryId) {

        Library desiredLibrary = validateLibrary(libraryId);

        if (desiredLibrary == null) {
            return notFound("Library not found");
        }

        return ok(desiredLibrary);

    }

    public ResponseEntity<Object> createLibrary(UUID userId) {

        User currentUser = validateUser(userId);

        if (currentUser == null) {
            return notFound("User not found");
        }

        if (this.libraryRepository.existsByUser(currentUser)) {
            return conflict("User already has a library");
        }

        LibraryRecordDTO libraryDTO = new LibraryRecordDTO(currentUser);
        Library newLibrary = new Library();

        BeanUtils.copyProperties(libraryDTO, newLibrary);
        return created(newLibrary);

    }

    public ResponseEntity<Object> deleteLibrary(UUID libraryId) {

        Library currentLibrary = validateLibrary(libraryId);

        if (currentLibrary == null) {
            return notFound("Library not found");
        }

        this.libraryRepository.deleteById(libraryId);
        return ok("Library has been deleted");

    }

    // Update library methods

    public ResponseEntity<Object> cleanLibrary(UUID libraryId) {

        Library currentLibrary = validateLibrary(libraryId);

        if (currentLibrary == null) {
            return notFound("Library not found");
        }

        if (currentLibrary.getBooks().isEmpty()) {
            return conflict("Selected library is already empty");
        }

        List<Book> emptyBookList = new ArrayList<>();

        currentLibrary.setBooks(emptyBookList);
        this.libraryRepository.save(currentLibrary);

        return ok("The library has been cleaned");

    }

    public ResponseEntity<Object> addBook(UUID libraryId, UUID bookId) {

        Library currentLibrary = validateLibrary(libraryId);

        if (currentLibrary == null) {
            return notFound("Library not found");
        }

        Book bookToBeAdded = validateBook(bookId);

        if (bookToBeAdded == null) {
            return notFound("Book not found");
        }

        if (currentLibrary.getBooks().contains(bookToBeAdded)) {
            return conflict("Book already registered on selected library");
        }

        currentLibrary.addBook(bookToBeAdded);

        return ok(this.libraryRepository.save(currentLibrary));

    }

    public ResponseEntity<Object> deleteBook(UUID libraryId, UUID bookId) {

        Library currentLibrary = validateLibrary(libraryId);

        if (currentLibrary == null) {
            return notFound("Library not found");
        }

        Book bookToBeDeleted = validateBook(bookId);

        if (bookToBeDeleted == null) {
            return notFound("Book not found");
        }

        if (!currentLibrary.getBooks().contains(bookToBeDeleted)) {
            return conflict("Book is not registered on selected library");
        }

        currentLibrary.deleteBook(bookToBeDeleted);
        this.libraryRepository.save(currentLibrary);
        return ok("Selected book has been successfully deleted on selected library");

    }

    // Methods for checking existance of objects in repository

    private Library validateLibrary(UUID libraryId) {
        return this.libraryRepository.findById(libraryId).orElse(null);
    }

    private User validateUser(UUID userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    private Book validateBook(UUID bookId) {
        return this.bookRepository.findById(bookId).orElse(null);
    }

    // Methods for handling common responses

    private ResponseEntity<Object> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity<Object> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    private ResponseEntity<Object> ok(Object body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    private ResponseEntity<Object> created(Object body) {
        if (body instanceof Library) {
            Library savedLibrary = this.libraryRepository.save((Library) body);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLibrary);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid object type");
    }

}
