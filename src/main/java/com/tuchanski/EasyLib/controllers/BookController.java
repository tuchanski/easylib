package com.tuchanski.EasyLib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.tuchanski.EasyLib.models.DTOs.BookRecordDTO;
import com.tuchanski.EasyLib.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "api/books", produces = {"application/json"})
@Tag(name = "easylib-openapi")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books registered in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns all books registered in database successfully"),
        @ApiResponse(responseCode = "404", description = "No books registered in database")
    })
    @GetMapping
    private ResponseEntity<Object> getAll() {
        return this.bookService.getAll();
    }
    
    @Operation(summary = "Get book registered in database by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns selected book registered in database successfully"),
        @ApiResponse(responseCode = "404", description = "Selected book not found")
    })
    @GetMapping("/{bookId}")
    private ResponseEntity<Object> getById(@PathVariable(value = "bookId") UUID bookId){
        return this.bookService.getById(bookId);
    }

    @Operation(summary = "Add new book in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Added new book in database successfully"),
        @ApiResponse(responseCode = "409", description = "Book with same titile, author and genre is already registered"),
        @ApiResponse(responseCode = "400", description = "Genre ENUM validation failed")
    })
    @PostMapping
    private ResponseEntity<Object> createBook(@RequestBody @Valid BookRecordDTO newBook) {
        return this.bookService.createBook(newBook);
    }

    @Operation(summary = "Delete selected book in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deletes selected book in database successfully"),
        @ApiResponse(responseCode = "404", description = "Selected book not found")
    })
    @DeleteMapping("/{bookId}")
    private ResponseEntity<Object> deleteBook(@PathVariable(value = "bookId") UUID bookId){
        return this.bookService.deleteBook(bookId);
    }

    @Operation(summary = "Update selected book in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updates selected book in database successfully"),
        @ApiResponse(responseCode = "404", description = "Selected book not found"),
        @ApiResponse(responseCode = "409", description = "Book with same title and author is already registered")
    })
    @PutMapping("/{bookId}")
    private ResponseEntity<Object> updateBook(@PathVariable(value = "bookId") UUID bookId, @RequestBody @Valid BookRecordDTO updatedBookDTO){
        return this.bookService.updateBook(bookId, updatedBookDTO);
    }

}
