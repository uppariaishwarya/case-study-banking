package com.bank.transaction_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.bank.transaction_service.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Handle runtime errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {

        ApiResponse response = new ApiResponse(
                ex.getMessage(),
                "FAILED"
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ✅ Handle any unknown exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {

        ApiResponse response = new ApiResponse(
                "Something went wrong ❌",
                "FAILED"
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                           .getFieldError()
                           .getDefaultMessage();

        ApiResponse response = new ApiResponse(message, "FAILED");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}