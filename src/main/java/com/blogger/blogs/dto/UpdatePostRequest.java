package com.blogger.blogs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePostRequest {

    @NotNull
    private Long id;

    @Size(max = 100, message = "Title too Long")
    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
