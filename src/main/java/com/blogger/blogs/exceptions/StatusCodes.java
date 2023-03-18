package com.blogger.blogs.exceptions;

public enum StatusCodes {

    COMMENT_NOT_FOUND("6002", "The comment  not found"),
    COMMENT_TO_DELETE_NOT_FOUND("6003", "The Comment trying to be deleted not found"),
    POST_NOT_FOUND("5002", "Post with given id not found"),
    POST_TO_DELETE_NOT_FOUND("5003", "The Post trying to be deleted not found"),
    COMMENT_EXISTS_FOR_POST("5004", "The post has an existing comment"),
    ;


    private String statusCode;
    private String statusDescription;

    StatusCodes(String statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
