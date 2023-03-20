package com.blogger.blogs.dto;


public class UserContext {
    private final Long id;
    private final String email;

    public UserContext(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", email='" + getEmail() + "'" +
                "}";
    }

}