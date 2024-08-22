package com.tuchanski.EasyLib.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tuchanski.EasyLib.models.Book;
import com.tuchanski.EasyLib.models.DTOs.BookRecordDTO;
import com.tuchanski.EasyLib.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAll());
    }

    public ResponseEntity<Object> createBook(BookRecordDTO bookDTO) {

        Book newBook = new Book();

        try {
            BeanUtils.copyProperties(bookDTO, newBook);
            return ResponseEntity.status(HttpStatus.OK).body(bookRepository.save(newBook));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating new book");
        }

    }

}
