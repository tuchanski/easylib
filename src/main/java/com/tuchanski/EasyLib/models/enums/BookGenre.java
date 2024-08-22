package com.tuchanski.EasyLib.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BookGenre {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    MYSTERY("Mystery"),
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    HORROR("Horror"),
    HISTORICAL("Historical"),
    BIOGRAPHY("Biography"),
    SELF_HELP("Self-Help"),
    POETRY("Poetry"),
    DRAMA("Drama");

    private final String displayName;

    BookGenre(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
