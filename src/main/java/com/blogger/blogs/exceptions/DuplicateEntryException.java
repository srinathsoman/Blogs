package com.blogger.blogs.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateEntryException extends APIException {

    public DuplicateEntryException(String statusCode, String statusDescription) {
        super(statusCode, statusDescription);
    }
}
