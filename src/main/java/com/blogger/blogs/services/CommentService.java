package com.blogger.blogs.services;

import com.blogger.blogs.dto.AddCommentRequest;
import com.blogger.blogs.dto.CommentDetails;
import com.blogger.blogs.dto.CommentInfo;
import com.blogger.blogs.dto.UpdateCommentRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentDetails addComment(Long postId, @Valid AddCommentRequest addCommentRequest, Long userId);

    Page<CommentInfo> getComments(Long postId , final Pageable pageable);

    CommentDetails getCommentDetails(Long id);

    void deleteComment(Long id, Long userId);

    CommentDetails updateComment(@Valid UpdateCommentRequest updateCommentRequest, Long userId);
}
