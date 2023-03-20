package com.blogger.blogs.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedModificationException extends APIException {

    public UnauthorizedModificationException(String statusCode, String statusDescription) {
        super(statusCode, statusDescription);
    }
}
