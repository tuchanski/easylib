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
@RequestMapping(value = "/users", produces = {"application/json"})
@Tag(name = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users registered in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns all registered users in database"),
        @ApiResponse(responseCode = "404", description = "No users registered in database")
    })
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return this.userService.getAll();
    }

    @Operation(summary = "Get registered user in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns selected user registered in database"),
        @ApiResponse(responseCode = "404", description = "Selected user not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getById(@PathVariable(value = "userId") UUID userId) {
        return this.userService.getById(userId);
    }

    @Operation(summary = "Add new user in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Added new user in database"),
        @ApiResponse(responseCode = "409", description = "Failed to validate user DTO")
    })
    @PostMapping
    private ResponseEntity<Object> createUser(@RequestBody @Valid UserRecordDTO newUser) {
        return this.userService.createUser(newUser);
    }

    @Operation(summary = "Delete selected user in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted selected user in database"),
        @ApiResponse(responseCode = "404", description = "Selected user not found")
    })
    @DeleteMapping("/{userId}")
    private ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        return this.userService.deleteUser(userId);
    }

    @Operation(summary = "Delete selected user in database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated selected user in database"),
        @ApiResponse(responseCode = "404", description = "Selected user not found"),
        @ApiResponse(responseCode = "409", description = "Failed to validate new user info by DTO")
    })
    @PutMapping("/{userId}")
    private ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Valid UserRecordDTO userDTO) {
        return this.userService.updateUser(userId, userDTO);
    }

}
