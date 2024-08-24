package com.tuchanski.EasyLib.services;

import java.util.Optional;
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

    public ResponseEntity<Object> createLibrary(UUID userId) {

        Optional<User> existingUserOpt = this.userRepository.findById(userId);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = existingUserOpt.get();

        LibraryRecordDTO libraryDTO = new LibraryRecordDTO(user);
        Library newLibrary = new Library();

        BeanUtils.copyProperties(libraryDTO, newLibrary);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.libraryRepository.save(newLibrary));

    }

    public ResponseEntity<Object> deleteLibrary(UUID id) {

        Optional<Library> existingLibraryOpt = this.libraryRepository.findById(id);

        if (existingLibraryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Library not found");
        }

        this.libraryRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Library has been deleted");

    }

    public ResponseEntity<Object> addBook(UUID libraryId, UUID bookId) {

        Optional<Library> existingLibraryOpt = this.libraryRepository.findById(libraryId);

        if (existingLibraryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Library not found");
        }

        Library currentLibrary = existingLibraryOpt.get();

        Optional<Book> existingBookOpt = this.bookRepository.findById(bookId);

        if (existingBookOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        Book bookToBeAdded = existingBookOpt.get();

        if (currentLibrary.getBooks().contains(bookToBeAdded)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book already registered");
        }

        currentLibrary.addBook(bookToBeAdded);

        return ResponseEntity.status(HttpStatus.OK).body(this.libraryRepository.save(currentLibrary));

    }

}
