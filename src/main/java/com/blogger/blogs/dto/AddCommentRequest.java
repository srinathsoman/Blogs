package com.blogger.blogs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddCommentRequest {


    @Size(max = 100, message = "Comment too Long")
    @NotBlank
    private String comment;
}
