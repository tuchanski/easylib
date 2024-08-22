package com.tuchanski.EasyLib.models.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDTO(
    @NotBlank(message = "Username is mandatory") String username,
    @NotBlank(message = "Email is mandatory") String email, 
    @NotBlank(message = "Password is mandatory") String password) {
}
