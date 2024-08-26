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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "Get all users registered on EasyLib database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Return users registered on EasyLib database"),
        @ApiResponse(responseCode = "400", description = "There is no registered users on EasyLib database")
    })
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getById(@PathVariable(value = "userId") UUID userId) {
        return this.userService.getById(userId);
    }

    @PostMapping
    private ResponseEntity<Object> createUser(@RequestBody @Valid UserRecordDTO newUser) {
        return this.userService.createUser(newUser);
    }

    @DeleteMapping("/{userId}")
    private ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        return this.userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    private ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Valid UserRecordDTO userDTO) {
        return this.userService.updateUser(userId, userDTO);
    }

}
