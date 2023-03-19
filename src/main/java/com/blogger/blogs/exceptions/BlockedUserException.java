package com.blogger.blogs.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BlockedUserException extends APIException{

    public BlockedUserException(String statusCode, String statusDescription) {
        super(statusCode, statusDescription);
    }
}
