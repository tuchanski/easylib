package com.tuchanski.EasyLib.models.interfaces;

import java.util.UUID;

import com.tuchanski.EasyLib.models.Book;
import com.tuchanski.EasyLib.models.Library;
import com.tuchanski.EasyLib.models.User;

public interface EntityValidationHandler {
    Library validateLibrary(UUID libraryId);

    User validateUser(UUID userId);

    Book validateBook(UUID bookId);
}
