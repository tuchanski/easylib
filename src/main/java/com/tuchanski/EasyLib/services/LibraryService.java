package com.tuchanski.EasyLib.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tuchanski.EasyLib.models.Library;
import com.tuchanski.EasyLib.models.User;
import com.tuchanski.EasyLib.models.DTOs.LibraryRecordDTO;
import com.tuchanski.EasyLib.repositories.LibraryRepository;
import com.tuchanski.EasyLib.repositories.UserRepository;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.libraryRepository.findAll());
    }

    public ResponseEntity<Object> createLibrary(UUID userId) {

        Optional<User> existingUserOpt = this.userRepository.findById(userId);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = existingUserOpt.get();

        LibraryRecordDTO libraryDTO = new LibraryRecordDTO(user);
        Library newLibrary = new Library();

        BeanUtils.copyProperties(libraryDTO, newLibrary);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.libraryRepository.save(newLibrary));

    }

}
