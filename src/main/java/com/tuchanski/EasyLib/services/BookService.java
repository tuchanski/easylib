package com.tuchanski.EasyLib.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tuchanski.EasyLib.models.Book;
import com.tuchanski.EasyLib.models.dtos.BookRecordDTO;
import com.tuchanski.EasyLib.models.enums.BookGenre;
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
            BookGenre validatedBookGenre = validateBookGenre(bookDTO.genre().toString());
    
            if (validatedBookGenre == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Genre not valid");
            }
    
            newBook.setGenre(validatedBookGenre); 
    
            BeanUtils.copyProperties(bookDTO, newBook);
    
            Book savedBook = bookRepository.save(newBook);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid genre: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    
    private BookGenre validateBookGenre(String genreToValidate) {
        try {
            return BookGenre.valueOf(genreToValidate.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    

}
