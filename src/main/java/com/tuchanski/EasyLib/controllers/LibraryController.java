package com.tuchanski.EasyLib.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuchanski.EasyLib.services.LibraryService;

@RestController
@RequestMapping(value = "/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.libraryService.getAll();
    }

    @GetMapping("{libraryId}")
    public ResponseEntity<Object> getById(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.getLibraryById(libraryId);
    }

    @PostMapping("{userId}")
    public ResponseEntity<Object> createLibrary(@PathVariable(value = "userId") UUID userId) {
        return this.libraryService.createLibrary(userId);
    }

    @DeleteMapping("{libraryId}")
    public ResponseEntity<Object> deleteLibrary(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.deleteLibrary(libraryId);
    }

    @DeleteMapping("/clean/{libraryId}")
    public ResponseEntity<Object> cleanLibrary(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.cleanLibrary(libraryId);
    }

    // Library has Book Operations Below -> Add and Delete book

    @PostMapping("/addBook/{libraryId}/{bookId}")
    public ResponseEntity<Object> addBook(@PathVariable(value = "libraryId") UUID libraryId,
            @PathVariable(value = "bookId") UUID bookId) {
        return this.libraryService.addBook(libraryId, bookId);
    }

    @DeleteMapping("/delBook/{libraryId}/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "libraryId") UUID libraryId,
            @PathVariable(value = "bookId") UUID bookId) {
        return this.libraryService.deleteBook(libraryId, bookId);
    }

}
