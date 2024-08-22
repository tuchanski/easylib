package com.tuchanski.EasyLib.models.DTOs;

import jakarta.validation.constraints.NotBlank;

public record BookRecordDTO(@NotBlank(message = "Title is mandatory") String title,
        @NotBlank(message = "Author is mandatory") String author,
        @NotBlank(message = "Description is mandatory") String description) {

}
