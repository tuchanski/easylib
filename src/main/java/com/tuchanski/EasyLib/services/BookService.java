package com.tuchanski.EasyLib.services;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private DefaultEntityValidationHandler entityValidationHandler;
    @Autowired
    private ResponseHandler responseHandler;

    public ResponseEntity<Object> getAll() {
        return responseHandler.ok(this.bookRepository.findAll());
    }

    public ResponseEntity<Object> getById(UUID bookId){
        Book desiredBook = entityValidationHandler.validateBook(bookId);

        if (desiredBook == null){
            return responseHandler.notFound("Book not found");
        }

        return responseHandler.ok(desiredBook);
    }

    public ResponseEntity<Object> createBook(BookRecordDTO bookDTO) {
        Book newBook = new Book();
        
        BeanUtils.copyProperties(bookDTO, newBook, "genre");
        BookGenre validatedGenre = getGenreByDisplayName(bookDTO.genre().toString());

        newBook.setGenre(validatedGenre);
        
        try {
            if (!this.bookRepository.existsByTitleAndAuthorAndGenre(newBook.getTitle(), newBook.getAuthor(), validatedGenre)){
                return responseHandler.created(this.bookRepository.save(newBook));
            }
            return responseHandler.conflict("Book already exists");

        } catch (Exception e) {
            return responseHandler.badRequest(e.getMessage());
        }
    }

    public ResponseEntity<Object> deleteBook(UUID bookId){

        Book bookToBeDeleted = entityValidationHandler.validateBook(bookId);

        if (bookToBeDeleted == null){
            return responseHandler.notFound("Book not found");
        }

        this.bookRepository.deleteById(bookId);
        return responseHandler.ok("Book with ID [" + bookId + "] has been deleted");
    }

    public ResponseEntity<Object> updateBook(UUID bookId, BookRecordDTO newInfoDTO){

        Book bookToBeUpdated = entityValidationHandler.validateBook(bookId);

        if (bookToBeUpdated == null){
            return responseHandler.notFound("Book not found");
        }

        BookGenre validatedGenre = getGenreByDisplayName(newInfoDTO.genre().toString());
        
        validateBookUpdateDTO(newInfoDTO, bookToBeUpdated);

        bookToBeUpdated.setTitle(newInfoDTO.title());
        bookToBeUpdated.setDescription(newInfoDTO.description());
        bookToBeUpdated.setAuthor(newInfoDTO.author());
        bookToBeUpdated.setGenre(validatedGenre);

        try {
            return responseHandler.ok(this.bookRepository.save(bookToBeUpdated));

        } catch (Exception e){
            return responseHandler.badRequest(e.getMessage());
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

    private void validateBookUpdateDTO(BookRecordDTO bookDTO, Book book){

        if (this.bookRepository.existsByTitle(bookDTO.title()) && !bookDTO.title().equals(book.getTitle())) {
            if (this.bookRepository.existsByAuthor(bookDTO.author()) && !bookDTO.author().equals(book.getAuthor())){
                throw new IllegalArgumentException("Book with inserted title and author is already registered");
            }
        }

    }

}
