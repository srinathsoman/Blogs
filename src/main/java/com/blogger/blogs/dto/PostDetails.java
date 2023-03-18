package com.blogger.blogs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDetails extends PostInfo {
    private String content;

    public PostDetails(Long id, String title, String content) {

        super(id,title);
        this.content=content;
    }
}
