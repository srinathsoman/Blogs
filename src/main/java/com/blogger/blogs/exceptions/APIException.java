package com.blogger.blogs.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIException extends RuntimeException{

    private String statusCode;
    private String statusDescription;
}
