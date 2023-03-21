package com.blogger.blogs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostRequest {
    @Size(max = 200, message = "Title too Long")
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
