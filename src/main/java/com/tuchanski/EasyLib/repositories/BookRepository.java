package com.tuchanski.EasyLib.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuchanski.EasyLib.models.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
    
}
