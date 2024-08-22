package com.tuchanski.EasyLib.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuchanski.EasyLib.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    boolean existsByTitleAndAuthor(String title, String author);
    
}
