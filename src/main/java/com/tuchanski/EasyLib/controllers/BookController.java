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

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    private ResponseEntity<Object> getAll() {
        return this.bookService.getAll();
    }

    @GetMapping("/{bookId}")
    private ResponseEntity<Object> getById(@PathVariable(value = "bookId") UUID bookId){
        return this.bookService.getById(bookId);
    }

    @PostMapping
    private ResponseEntity<Object> createBook(@RequestBody @Valid BookRecordDTO newBook) {
        return this.bookService.createBook(newBook);
    }

    @DeleteMapping("/{bookId}")
    private ResponseEntity<Object> deleteBook(@PathVariable(value = "bookId") UUID bookId){
        return this.bookService.deleteBook(bookId);
    }

    @PutMapping("/{bookId}")
    private ResponseEntity<Object> updateBook(@PathVariable(value = "bookId") UUID bookId, @RequestBody @Valid BookRecordDTO updatedBookDTO){
        return this.bookService.updateBook(bookId, updatedBookDTO);
    }

}
