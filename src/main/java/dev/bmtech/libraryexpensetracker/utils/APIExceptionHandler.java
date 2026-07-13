package dev.bmtech.libraryexpensetracker.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class APIExceptionHandler {

    // Since the error messages returned by the server were less clear and exposed
    // the stack trace, I made this class to "overwrite" those messages

    // 404, user requested an invalid endpoint
    // Example: "ANY /some_endpoint_that_doesnt_exist"

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFoundException(
            NoResourceFoundException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", "Resource was not found");

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    // 400, user provided an invalid type in the url
    // Example: "DELETE /transaction/some_string_instead_of_an_int"

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", "Invalid request parameter");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    // 400, user did not provide a valid request body
    // Example: "POST /transaction/", missing/invalid request body

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", "Request body is missing or in an invalid format");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

}