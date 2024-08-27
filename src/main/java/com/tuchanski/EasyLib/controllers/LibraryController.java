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

    @Operation(summary = "Get all libraries registered in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns all registered libraries in database"),
        @ApiResponse(responseCode = "404", description = "No registered libraries in database")
    })
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.libraryService.getAll();
    }

    @Operation(summary = "Get registered library in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns selected library in database"),
        @ApiResponse(responseCode = "404", description = "Selected library not found")
    })
    @GetMapping("{libraryId}")
    public ResponseEntity<Object> getById(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.getLibraryById(libraryId);
    }

    @Operation(summary = "Add new library in database by user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Added new library in database successfully"),
        @ApiResponse(responseCode = "409", description = "Selected user already has a library")
    })
    @PostMapping("{userId}")
    public ResponseEntity<Object> createLibrary(@PathVariable(value = "userId") UUID userId) {
        return this.libraryService.createLibrary(userId);
    }

    @Operation(summary = "Delete selected library in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deletes selected library in database successfully"),
        @ApiResponse(responseCode = "404", description = "Selected library not found")
    })
    @DeleteMapping("{libraryId}")
    public ResponseEntity<Object> deleteLibrary(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.deleteLibrary(libraryId);
    }

    @Operation(summary = "Clean selected library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Selected library has been cleaned"),
        @ApiResponse(responseCode = "404", description = "Selected library not found"),
        @ApiResponse(responseCode = "409", description = "Selected library is already empty")
    })
    @DeleteMapping("/clean/{libraryId}")
    public ResponseEntity<Object> cleanLibrary(@PathVariable(value = "libraryId") UUID libraryId) {
        return this.libraryService.cleanLibrary(libraryId);
    }

    @Operation(summary = "Add selected book to selected library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added selected book to selected library"),
        @ApiResponse(responseCode = "404", description = "Selected library not found or selected book not found"),
        @ApiResponse(responseCode = "409", description = "Selected book is already registered in selected library")
    })
    @PostMapping("/addBook/{libraryId}/{bookId}")
    public ResponseEntity<Object> addBook(@PathVariable(value = "libraryId") UUID libraryId,
            @PathVariable(value = "bookId") UUID bookId) {
        return this.libraryService.addBook(libraryId, bookId);
    }

    @Operation(summary = "Delete selected book to selected library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted selected book in selected library"),
        @ApiResponse(responseCode = "404", description = "Selected library not found or selected book not found"),
        @ApiResponse(responseCode = "409", description = "Selected book is not registered in selected library")
    })
    @DeleteMapping("/delBook/{libraryId}/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "libraryId") UUID libraryId,
            @PathVariable(value = "bookId") UUID bookId) {
        return this.libraryService.deleteBook(libraryId, bookId);
    }

}
