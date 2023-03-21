package com.blogger.blogs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@NoArgsConstructor
public class PostDetails extends PostInfo {
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public PostDetails(Long id, String title, String content) {

        super(id,title);
        this.content=content;
    }
}
