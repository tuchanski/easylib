package com.tuchanski.EasyLib.services;

import org.springframework.beans.BeanUtils;

import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private DefaultEntityValidationHandler entityValidationHandler;
    @Autowired
    private ResponseHandler responseHandler;

    private PasswordEncoder passwordEncoder;
    private EmailValidator emailValidator;

    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.emailValidator = EmailValidator.getInstance();
    }

    public ResponseEntity<Object> getAll() {
        return responseHandler.ok(this.userRepository.findAll());
    }

    public ResponseEntity<Object> getById(UUID userId) {

        User desiredUser = entityValidationHandler.validateUser(userId);

        if (desiredUser == null) {
            return responseHandler.notFound("User not found");
        }

        return responseHandler.ok(desiredUser);

    }

    public ResponseEntity<Object> createUser(UserRecordDTO userDTO) {

        User newUser = new User();

        validateUserCreation(userDTO);

        try {
            BeanUtils.copyProperties(userDTO, newUser);
            newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
            return responseHandler.created(this.userRepository.save(newUser));

        } catch (Exception e) {
            return responseHandler.badRequest(e.getMessage());
        }

    }

    public ResponseEntity<Object> deleteUser(UUID userId) {

        User userToBeDeleted = entityValidationHandler.validateUser(userId);

        if (userToBeDeleted == null) {
            return responseHandler.notFound("User not found");
        }

        this.userRepository.deleteById(userId);
        return responseHandler.ok("User with ID " + userId + " has been deleted");

    }

    public ResponseEntity<Object> updateUser(UUID userId, UserRecordDTO newInfoDTO) {

        User userToBeUpdated = entityValidationHandler.validateUser(userId);

        if (userToBeUpdated == null) {
            return responseHandler.notFound("User not found");
        }

        validateUserUpdateDTO(newInfoDTO, userToBeUpdated);

        if (!this.passwordEncoder.matches(newInfoDTO.password(), userToBeUpdated.getPassword())) {
            userToBeUpdated.setPassword(this.passwordEncoder.encode(newInfoDTO.password()));
        }

        BeanUtils.copyProperties(newInfoDTO, userToBeUpdated, "password");

        try {
            return responseHandler.ok(this.userRepository.save(userToBeUpdated));

        } catch (Exception e) {
            return responseHandler.badRequest(e.getMessage());
        }
    }

    private void validateUserCreationDTO(UserRecordDTO userDTO) {

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

    }

    private void validateUserUpdateDTO(UserRecordDTO userDTO, User user){

        if (!emailValidator.isValid(userDTO.email())) {
            throw new IllegalArgumentException("E-mail format is not valid");
        }

        if (userRepository.existsByEmail(userDTO.email()) && !userDTO.email().equals(user.getEmail())) {
            throw new IllegalArgumentException("E-mail is already registered");
        }

        if (userRepository.existsByUsername(userDTO.username()) && !userDTO.username().equals(user.getUsername())) {
            throw new IllegalArgumentException("Username is already registered");
        }

        if (userDTO.password().length() < 5) {
            throw new IllegalArgumentException("Password is too short - Must have at least 5 characters.");
        }
    }

    
}
