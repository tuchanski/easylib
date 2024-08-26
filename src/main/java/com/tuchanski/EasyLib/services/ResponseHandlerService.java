package com.tuchanski.EasyLib.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseHandlerService {

    public ResponseEntity<Object> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    public ResponseEntity<Object> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    public ResponseEntity<Object> ok(Object body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public ResponseEntity<Object> created(Object body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public ResponseEntity<Object> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
