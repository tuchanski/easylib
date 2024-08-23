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

    @GetMapping("/{id}")
    private ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id){
        return this.bookService.getById(id);
    }

    @PostMapping
    private ResponseEntity<Object> createBook(@RequestBody @Valid BookRecordDTO newBook) {
        return this.bookService.createBook(newBook);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteBook(@PathVariable(value = "id") UUID id){
        return this.bookService.deleteBook(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> updateBook(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookRecordDTO updatedBookDTO){
        return this.bookService.updateBook(id, updatedBookDTO);
    }

}
