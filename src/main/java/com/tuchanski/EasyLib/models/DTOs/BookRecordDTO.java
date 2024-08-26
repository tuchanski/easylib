package com.tuchanski.EasyLib.models.DTOs;

import com.tuchanski.EasyLib.models.enums.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRecordDTO(
        @NotBlank(message = "Title is mandatory") String title,
        @NotBlank(message = "Author is mandatory") String author,
        String description,
        @NotNull(message = "Genre is mandatory") BookGenre genre) {
}
