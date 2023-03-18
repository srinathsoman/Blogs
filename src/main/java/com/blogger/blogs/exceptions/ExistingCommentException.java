package com.blogger.blogs.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExistingCommentException extends APIException{

    public ExistingCommentException(String statusCode, String statusDescription) {
        super(statusCode, statusDescription);
    }
}