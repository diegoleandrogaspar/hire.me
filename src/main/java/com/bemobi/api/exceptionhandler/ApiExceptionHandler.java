package com.bemobi.api.exceptionhandler;

import com.bemobi.domain.exception.CustomAliasAlreadyExistsException;
import com.bemobi.domain.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Object> handleUrlNotFoundException(UrlNotFoundException ex) {
        Problem problem = createProblem("002", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(CustomAliasAlreadyExistsException.class)
    public ResponseEntity<Object> handleCustomAliasAlreadyExistsException(CustomAliasAlreadyExistsException ex) {
        Problem problem = createProblem("001", "CUSTOM ALIAS ALREADY EXISTS");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    private Problem createProblem(String errorCode, String description) {
        return Problem.builder()
                .err_code(errorCode)
                .description(description)
                .build();
    }

}

