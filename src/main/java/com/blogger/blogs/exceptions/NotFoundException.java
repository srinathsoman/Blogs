package com.blogger.blogs.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends APIException{


    public NotFoundException(String statusCode, String statusDescription) {
        super(statusCode, statusDescription);
    }
}
