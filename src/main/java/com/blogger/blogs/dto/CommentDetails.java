package com.blogger.blogs.dto;

import lombok.Data;

@Data
public class CommentDetails extends  CommentInfo{

    private String postId;
}
