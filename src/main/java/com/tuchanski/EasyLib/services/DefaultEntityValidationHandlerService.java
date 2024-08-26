package com.tuchanski.EasyLib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.tuchanski.EasyLib.models.Book;
import com.tuchanski.EasyLib.models.Library;
import com.tuchanski.EasyLib.models.User;
import com.tuchanski.EasyLib.repositories.BookRepository;
import com.tuchanski.EasyLib.repositories.LibraryRepository;
import com.tuchanski.EasyLib.repositories.UserRepository;
import com.tuchanski.EasyLib.models.interfaces.EntityValidationHandler;

@Service
public class DefaultEntityValidationHandlerService implements EntityValidationHandler {
    
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Library validateLibrary(UUID libraryId) {
        return libraryRepository.findById(libraryId).orElse(null);
    }

    @Override
    public User validateUser(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Book validateBook(UUID bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }
}
