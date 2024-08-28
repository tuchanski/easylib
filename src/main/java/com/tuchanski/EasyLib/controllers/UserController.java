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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "api/users", produces = {"application/json"})
@Tag(name = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Retrieve all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all registered users"),
        @ApiResponse(responseCode = "404", description = "Not found registered users")
    })
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.userService.getAll();
    }

    @Operation(summary = "Retrieve a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved selected user successfully"),
        @ApiResponse(responseCode = "404", description = "Not found selected user")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getById(@PathVariable(value = "userId") UUID userId) {
        return this.userService.getById(userId);
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new user successfully"),
        @ApiResponse(responseCode = "409", description = "Failed to validate user DTO")
    })
    @PostMapping
    private ResponseEntity<Object> createUser(@RequestBody @Valid UserRecordDTO newUser) {
        return this.userService.createUser(newUser);
    }

    @Operation(summary = "Delete a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted selected user successfully"),
        @ApiResponse(responseCode = "404", description = "Not found selected user")
    })
    @DeleteMapping("/{userId}")
    private ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        return this.userService.deleteUser(userId);
    }

    @Operation(summary = "Update a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated selected user successfully"),
        @ApiResponse(responseCode = "404", description = "Not found selected user"),
        @ApiResponse(responseCode = "409", description = "Failed to validate new user info by DTO")
    })
    @PutMapping("/{userId}")
    private ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Valid UserRecordDTO userDTO) {
        return this.userService.updateUser(userId, userDTO);
    }

}
