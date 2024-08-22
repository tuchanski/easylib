package com.tuchanski.EasyLib.models.dtos;

import com.tuchanski.EasyLib.models.enums.BookGenre;

import jakarta.validation.constraints.NotBlank;

public record BookRecordDTO(@NotBlank(message = "Title is mandatory") String title,
        @NotBlank(message = "Author is mandatory") String author,
        @NotBlank(message = "Description is mandatory") String description,
        BookGenre genre) {

}
