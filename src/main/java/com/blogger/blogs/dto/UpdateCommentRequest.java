package com.blogger.blogs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCommentRequest {

    @NotNull
    private Long Id;

    @Size(max = 160, message = "Comment too Long")
    @NotBlank
    private String comment;
}
