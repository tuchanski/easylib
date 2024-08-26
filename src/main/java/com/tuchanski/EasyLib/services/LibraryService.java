package com.tuchanski.EasyLib.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tuchanski.EasyLib.models.Book;
import com.tuchanski.EasyLib.models.Library;
import com.tuchanski.EasyLib.models.User;
import com.tuchanski.EasyLib.models.DTOs.LibraryRecordDTO;
import com.tuchanski.EasyLib.repositories.LibraryRepository;

import jakarta.transaction.Transactional;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private DefaultEntityValidationHandlerService entityValidationHandler;

    @Autowired
    private ResponseHandlerService responseHandler;

    public ResponseEntity<Object> getAll() {
        return responseHandler.ok(this.libraryRepository.findAll());
    }

    public ResponseEntity<Object> getLibraryById(UUID libraryId) {

        Library desiredLibrary = entityValidationHandler.validateLibrary(libraryId);

        if (desiredLibrary == null) {
            return responseHandler.notFound("Library not found");
        }

        return responseHandler.ok(desiredLibrary);

    }

    @Transactional
    public ResponseEntity<Object> createLibrary(UUID userId) {

        User currentUser = entityValidationHandler.validateUser(userId);

        if (currentUser == null) {
            return responseHandler.notFound("User not found");
        }

        if (this.libraryRepository.existsByUser(currentUser)) {
            return responseHandler.conflict("User already has a library");
        }

        LibraryRecordDTO libraryDTO = new LibraryRecordDTO(currentUser);
        Library newLibrary = new Library();

        BeanUtils.copyProperties(libraryDTO, newLibrary);
        return responseHandler.created(this.libraryRepository.save(newLibrary));

    }

    @Transactional
    public ResponseEntity<Object> deleteLibrary(UUID libraryId) {

        Library currentLibrary = entityValidationHandler.validateLibrary(libraryId);

        if (currentLibrary == null) {
            return responseHandler.notFound("Library not found");
        }

        this.libraryRepository.deleteById(libraryId);
        return responseHandler.ok("Library has been deleted");

    }

    @Transactional
    public ResponseEntity<Object> cleanLibrary(UUID libraryId) {

        Library currentLibrary = entityValidationHandler.validateLibrary(libraryId);

        if (currentLibrary == null) {
            return responseHandler.notFound("Library not found");
        }

        if (currentLibrary.getBooks().isEmpty()) {
            return responseHandler.conflict("Selected library is already empty");
        }

        List<Book> emptyBookList = new ArrayList<>();

        currentLibrary.setBooks(emptyBookList);
        this.libraryRepository.save(currentLibrary);

        return responseHandler.ok("The library has been cleaned");

    }

    @Transactional
    public ResponseEntity<Object> addBook(UUID libraryId, UUID bookId) {

        Library currentLibrary = entityValidationHandler.validateLibrary(libraryId);

        if (currentLibrary == null) {
            return responseHandler.notFound("Library not found");
        }

        Book bookToBeAdded = entityValidationHandler.validateBook(bookId);

        if (bookToBeAdded == null) {
            return responseHandler.notFound("Book not found");
        }

        if (currentLibrary.getBooks().contains(bookToBeAdded)) {
            return responseHandler.conflict("Book already registered on selected library");
        }

        currentLibrary.addBook(bookToBeAdded);

        return responseHandler.ok(this.libraryRepository.save(currentLibrary));

    }

    @Transactional
    public ResponseEntity<Object> deleteBook(UUID libraryId, UUID bookId) {

        Library currentLibrary = entityValidationHandler.validateLibrary(libraryId);

        if (currentLibrary == null) {
            return responseHandler.notFound("Library not found");
        }

        Book bookToBeDeleted = entityValidationHandler.validateBook(bookId);

        if (bookToBeDeleted == null) {
            return responseHandler.notFound("Book not found");
        }

        if (!currentLibrary.getBooks().contains(bookToBeDeleted)) {
            return responseHandler.conflict("Book is not registered on selected library");
        }

        currentLibrary.deleteBook(bookToBeDeleted);
        this.libraryRepository.save(currentLibrary);
        return responseHandler.ok("Selected book has been successfully deleted on selected library");
    }

}
