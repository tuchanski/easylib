package com.tuchanski.EasyLib.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tuchanski.EasyLib.models.Book;
import com.tuchanski.EasyLib.models.DTOs.BookRecordDTO;
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
        
        BeanUtils.copyProperties(bookDTO, newBook, "genre");

        BookGenre validatedGenre = getGenreByDisplayName(bookDTO.genre().toString());
        newBook.setGenre(validatedGenre);
        
        try {
            if (!bookRepository.existsByTitleAndAuthor(newBook.getTitle(), newBook.getAuthor())){
                return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(newBook));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    
    private BookGenre getGenreByDisplayName(String displayName){
        for (BookGenre genre : BookGenre.values()){
            if (genre.getDisplayName().equalsIgnoreCase(displayName)){
                return genre;
            }
        }
        throw new IllegalArgumentException("Genre not found: " + displayName);
    }


}
