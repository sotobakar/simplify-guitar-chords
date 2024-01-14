package com.patricksantino.simplifyguitarchords.advice;

import com.google.common.base.CaseFormat;
import com.patricksantino.simplifyguitarchords.dto.response.ErrorResponse;
import com.patricksantino.simplifyguitarchords.dto.response.ValidationErrorResponse;
import com.patricksantino.simplifyguitarchords.exception.HttpException;
import com.patricksantino.simplifyguitarchords.exception.ModelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setMessage("There are validation errors.");

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            // Check if the key is already in the HashMap
            Map<String, List<String>> errors = error.getErrors();

            String fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldError.getField());

            if (errors.containsKey(fieldName)) {
                List<String> existingList = errors.get(fieldName);

                existingList.add(fieldError.getDefaultMessage());
            } else {
                List<String> newList = new ArrayList<>();
                newList.add(fieldError.getDefaultMessage());
                errors.put(fieldName, newList);
            }
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler({ModelNotFoundException.class})
    public ResponseEntity<Object> handleModelNotFound(ModelNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({HttpException.class})
    public ResponseEntity<Object> handleHttpException(HttpException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(ex.getStatusCode());
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(error.getStatusCode()).body(error);
    }
}