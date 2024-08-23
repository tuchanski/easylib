package com.tuchanski.EasyLib.models.DTOs;

import com.tuchanski.EasyLib.models.User;

import jakarta.validation.constraints.NotNull;

public record LibraryRecordDTO(@NotNull(message = "User is mandatory") User user) {

}
