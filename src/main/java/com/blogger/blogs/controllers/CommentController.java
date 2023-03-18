package com.blogger.blogs.controllers;

import com.blogger.blogs.dto.AddCommentRequest;
import com.blogger.blogs.dto.CommentDetails;
import com.blogger.blogs.dto.CommentInfo;
import com.blogger.blogs.services.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides various REST APIs associated with comments
 *
 * @author Srinath
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "/posts/{post_id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentService commentService;

    /**
     * This API is used to fetch paginated list of all comments on a post.
     * @param postId
     * @param pageable
     * @return Page<CommentInfo>
     */
    @GetMapping
    Page<CommentInfo> getComments(@PathVariable(value = "post_id") final Long postId, Pageable pageable){
        return commentService.getComments(postId,pageable);
    }

    /**
     * The API  adds a comment to a Post
     * @param postId
     * @param addCommentRequest
     * @return CommentDetails
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CommentDetails addComment(@PathVariable(value = "post_id") final Long postId, @Valid @RequestBody AddCommentRequest addCommentRequest){
        return commentService.addComment(postId,addCommentRequest);
    }

    /**
     * The API fetch the details of the comment with given id
     * @param id
     * @return CommentDetails
     */
    @GetMapping("{id}")
    CommentDetails getCommentDetails(@PathVariable("id") final Long id){
        return  commentService.getCommentDetails(id);
    }

    /**
     * API to delete a comment with given id
     * @param id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteComment(@PathVariable("id") final Long id){
        commentService.deleteComment(id);
    }

}