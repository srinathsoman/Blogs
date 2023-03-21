package com.blogger.blogs.exceptions.handler;

import com.blogger.blogs.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice()
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BlockedUserException.class)
    public ResponseEntity<Object> handleBlockedUserException(BlockedUserException blockedUserException) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(blockedUserException.getStatusDescription());
    }

    @ExceptionHandler(ExistingCommentException.class)
    public ResponseEntity<Object> handleExistingCommentException(ExistingCommentException existingCommentException) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(existingCommentException.getStatusDescription());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(notFoundException.getStatusDescription());
    }

    @ExceptionHandler(UnauthorizedModificationException.class)
    public ResponseEntity<Object> handleUnauthorizedModificationException
            (UnauthorizedModificationException unauthorizedModificationException) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(unauthorizedModificationException.getStatusDescription());
    }


}
