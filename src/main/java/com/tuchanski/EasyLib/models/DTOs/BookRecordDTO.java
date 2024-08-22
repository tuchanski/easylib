package com.tuchanski.EasyLib.models.dtos;

import com.tuchanski.EasyLib.models.enums.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRecordDTO(
        @NotBlank(message = "Title is mandatory") String title,
        @NotBlank(message = "Author is mandatory") String author,
        @NotBlank(message = "Description is mandatory") String description,
        @NotNull(message = "Genre is mandatory") BookGenre genre) {
}
