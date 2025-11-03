package com.exemplo.crudmongo.exception;

import com.exemplo.crudmongo.exception.exceptions.CursoNotFound;
import com.exemplo.crudmongo.exception.exceptions.UserNotFound;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<?> userNotFound(UserNotFound e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(CursoNotFound.class)
    private ResponseEntity<?> cursoNotFound(CursoNotFound e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }


}
