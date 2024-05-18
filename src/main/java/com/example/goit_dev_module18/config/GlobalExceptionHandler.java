package com.example.goit_dev_module18.config;

import com.example.goit_dev_module18.exceptions.NotValidNoteException;
import com.example.goit_dev_module18.exceptions.NoteNotExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NoteNotExistException.class})
    public ResponseEntity<Map<String, List<String>>> noteNotFoundException(NoteNotExistException ex) {
        return getErrorsMap(ex, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {NotValidNoteException.class})
    public ResponseEntity<Map<String, List<String>>> noteIsNotValidException(NotValidNoteException ex) {
        return getErrorsMap(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Map<String, List<String>>> getErrorsMap(Throwable ex, HttpStatus status) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(map, new HttpHeaders(), status);
    }
}
