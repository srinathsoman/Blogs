package com.blogger.blogs.exceptions.handler;

import com.blogger.blogs.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice()
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(notFoundException.getStatusDescription());
    }



}
