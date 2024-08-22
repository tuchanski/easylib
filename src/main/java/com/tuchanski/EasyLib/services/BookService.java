package com.tuchanski.EasyLib.services;

import java.util.Optional;
import java.util.UUID;

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
        return ResponseEntity.status(HttpStatus.OK).body(this.bookRepository.findAll());
    }

    public ResponseEntity<Object> getById(UUID id){
        Optional<Book> existingBookOpt = this.bookRepository.findById(id);

        if (existingBookOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        Book isFound = existingBookOpt.get();

        return ResponseEntity.status(HttpStatus.OK).body(isFound);
    }

    public ResponseEntity<Object> createBook(BookRecordDTO bookDTO) {
        Book newBook = new Book();
        
        BeanUtils.copyProperties(bookDTO, newBook, "genre");

        BookGenre validatedGenre = getGenreByDisplayName(bookDTO.genre().toString());
        newBook.setGenre(validatedGenre);
        
        try {
            if (!this.bookRepository.existsByTitleAndAuthorAndGenre(newBook.getTitle(), newBook.getAuthor(), validatedGenre)){
                return ResponseEntity.status(HttpStatus.CREATED).body(this.bookRepository.save(newBook));
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book already exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> deleteBook(UUID id){

        Optional<Book> toBeDeleted = this.bookRepository.findById(id);

        if (toBeDeleted.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        this.bookRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book with ID " + id + " has been deleted");
    }

    public ResponseEntity<Object> updateBook(UUID id, BookRecordDTO newInfoDTO){

        Optional<Book> existingBookOpt = this.bookRepository.findById(id);

        if (existingBookOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        Book bookToBeUpdated = existingBookOpt.get();

        BookGenre validatedGenre = getGenreByDisplayName(newInfoDTO.genre().toString());

        boolean bookExists = this.bookRepository.existsByTitleAndAuthorAndGenre(newInfoDTO.title(), newInfoDTO.author(), validatedGenre);
        if (bookExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book already exists");
        }

        bookToBeUpdated.setTitle(newInfoDTO.title());
        bookToBeUpdated.setAuthor(newInfoDTO.author());
        bookToBeUpdated.setGenre(validatedGenre);

        try {
            Book updatedBook = this.bookRepository.save(bookToBeUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(updatedBook);

        } catch (Exception e){
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
