package com.blogger.blogs.services;

import com.blogger.blogs.dto.AddCommentRequest;
import com.blogger.blogs.dto.CommentDetails;
import com.blogger.blogs.dto.CommentInfo;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentDetails addComment(Long postId, @Valid AddCommentRequest addCommentRequest);

    Page<CommentInfo> getComments(Long postId , final Pageable pageable);

    CommentDetails getCommentDetails(Long id);

    void deleteComment(Long id);
}
