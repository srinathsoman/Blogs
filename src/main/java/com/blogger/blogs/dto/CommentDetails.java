package com.blogger.blogs.dto;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
public class CommentDetails extends  CommentInfo{

    private Long postId;
    private Date createdAt;
    private Date updatedAt;
}
