package com.tuchanski.EasyLib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return bookService.getAll();
    }

    @PostMapping
    private ResponseEntity<Object> createBook(@RequestBody @Valid BookRecordDTO newBook) {
        return bookService.createBook(newBook);
    }

}
