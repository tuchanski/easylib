package com.tuchanski.EasyLib.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuchanski.EasyLib.models.DTOs.UserRecordDTO;
import com.tuchanski.EasyLib.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
        return this.userService.getById(id);
    }

    @PostMapping
    private ResponseEntity<Object> createUser(@RequestBody @Valid UserRecordDTO newUser) {
        return this.userService.createUser(newUser);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        return this.userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid UserRecordDTO userDTO) {
        return this.userService.updateUser(id, userDTO);
    }

}
