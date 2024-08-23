package com.tuchanski.EasyLib.services;

import org.springframework.beans.BeanUtils;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuchanski.EasyLib.models.User;
import com.tuchanski.EasyLib.models.DTOs.UserRecordDTO;
import com.tuchanski.EasyLib.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private EmailValidator emailValidator;

    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.emailValidator = EmailValidator.getInstance();
    }

    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userRepository.findAll());
    }

    public ResponseEntity<Object> getById(UUID id) {

        Optional<User> existingUserOpt = this.userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User isFound = existingUserOpt.get();

        return ResponseEntity.status(HttpStatus.OK).body(isFound);

    }

    public ResponseEntity<Object> createUser(UserRecordDTO userDTO) {

        User newUser = new User();

        ResponseEntity<Object> validatedDtoRequest = validateUserDTO(userDTO);

        try {

            var treatedDto = (UserRecordDTO) validatedDtoRequest.getBody();
            BeanUtils.copyProperties(treatedDto, newUser);
            newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }

    }

    public ResponseEntity<Object> deleteUser(UUID id) {

        Optional<User> existingUserOpt = this.userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        this.userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User with ID " + id + " has been deleted");

    }

    private ResponseEntity<Object> validateUserDTO(UserRecordDTO userDTO) {

        if (!emailValidator.isValid(userDTO.email())) {
            throw new IllegalArgumentException("E-mail format is not valid");
        }

        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("E-mail is already registered");
        }

        if (userRepository.existsByUsername(userDTO.username())) {
            throw new IllegalArgumentException("Username is already registered");
        }

        if (userDTO.password().length() < 5) {
            throw new IllegalArgumentException("Password is too short - Must have at least 5 characters.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);

    }

}
