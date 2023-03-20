package com.blogger.blogs.exceptions;

public enum StatusCodes {

    COMMENT_NOT_FOUND("6002", "The comment  not found"),
    COMMENT_TO_DELETE_NOT_FOUND("6003", "The Comment trying to be deleted not found"),
    UNAUTHORIZED_EDIT_COMMENT("6005", "Only the creator of the comment can edit a post"),
    UNAUTHORIZED_DELETE_COMMENT("6006", "Only the creator of the comment can delete a post"),
    POST_NOT_FOUND("5002", "Post with given id not found"),
    POST_TO_DELETE_NOT_FOUND("5003", "The Post trying to be deleted not found"),
    COMMENT_EXISTS_FOR_POST("5004", "The post has an existing comment"),
    UNAUTHORIZED_EDIT_POST("5005", "Only the creator of the post can edit a post"),
    UNAUTHORIZED_DELETE_POST("5006", "Only the creator of the post can delete a post"),
    AUTHOR_BLOCKED("5007", "The Author of the post is blocked by current user"),
    ALREADY_BLOCKED_USER("7001", "The user is already blocked"),
    USER_NOT_BLOCKED("7002", "The user is not blocked.")
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
