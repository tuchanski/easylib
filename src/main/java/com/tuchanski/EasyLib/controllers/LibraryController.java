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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "api/libraries", produces = {"application/json"})
@Tag(name = "libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Operation(summary = "Retrieve all libraries")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all registered libraries"),
        @ApiResponse(responseCode = "404", description = "Not found registered libraries")
    })
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.libraryService.getAll();
    }

    @Operation(summary = "Retrieve a library by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved selected library successfully"),
        @ApiResponse(responseCode = "404", description = "Not found selected library")
    })
    @GetMapping("{libraryId}")
    public ResponseEntity<Object> getById(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.getLibraryById(libraryId);
    }

    @Operation(summary = "Create a library for a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new library successfully"),
        @ApiResponse(responseCode = "409", description = "Selected user already has a library")
    })
    @PostMapping("{userId}")
    public ResponseEntity<Object> createLibrary(@PathVariable(value = "userId") UUID userId) {
        return this.libraryService.createLibrary(userId);
    }

    @Operation(summary = "Delete a library by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted selected library successfully"),
        @ApiResponse(responseCode = "404", description = "Not found selected library")
    })
    @DeleteMapping("{libraryId}")
    public ResponseEntity<Object> deleteLibrary(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.deleteLibrary(libraryId);
    }

    @Operation(summary = "Clear all books from a library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Selected library has been cleaned successfully"),
        @ApiResponse(responseCode = "404", description = "Not found selected library"),
        @ApiResponse(responseCode = "409", description = "Selected library is already empty")
    })
    @DeleteMapping("/clear/{libraryId}")
    public ResponseEntity<Object> cleanLibrary(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.cleanLibrary(libraryId);
    }

    @Operation(summary = "Add a book to a library by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added selected book to library successfully"),
        @ApiResponse(responseCode = "404", description = "Not found selected library or selected book not found"),
        @ApiResponse(responseCode = "409", description = "Selected book is already registered in library")
    })
    @PostMapping("/{libraryId}/add-book/{bookId}")
    public ResponseEntity<Object> addBook(@PathVariable(value = "libraryId") UUID libraryId,
            @PathVariable(value = "bookId") UUID bookId) {
        return this.libraryService.addBook(libraryId, bookId);
    }

    @Operation(summary = "Remove a book from a library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Removed selected book in library"),
        @ApiResponse(responseCode = "404", description = "Not found selected library or selected book not found"),
        @ApiResponse(responseCode = "409", description = "Selected book is not registered in library")
    })
    @DeleteMapping("/{libraryId}/del-book/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "libraryId") UUID libraryId,
            @PathVariable(value = "bookId") UUID bookId) {
        return this.libraryService.deleteBook(libraryId, bookId);
    }

}
