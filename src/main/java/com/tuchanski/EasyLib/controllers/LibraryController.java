package com.tuchanski.EasyLib.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuchanski.EasyLib.services.LibraryService;

@RestController
@RequestMapping(value = "/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.libraryService.getAll();
    }

    @PostMapping("{idUser}")
    public ResponseEntity<Object> createLibrary(@PathVariable(value = "idUser") UUID userId){
        return this.libraryService.createLibrary(userId);
    }

}
