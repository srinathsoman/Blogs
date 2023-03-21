package com.blogger.blogs.controllers;

import com.blogger.blogs.dto.*;
import com.blogger.blogs.services.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides various REST APIs for operations on comments of a post.
 * @author Srinath
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "/posts/{post_id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentService commentService;

    /**
     * This API is used to fetch paginated list of all comments on a post.
     * User information has to be passed as a valid JWT Token in header.
     * @param postId id of post for which comments have to be fetched
     * @param pageable the paging details
     * @return Paged list of comments of this post
     */
    @GetMapping
    Page<CommentInfo> getComments(@PathVariable(value = "post_id") final Long postId, Pageable pageable){
        return commentService.getComments(postId,pageable);
    }

    /**
     * The API  adds a comment to a Post.
     * User information has to be passed as a valid JWT Token in header.
     * @param postId id of the post for which comment has to be added.
     * @param addCommentRequest The details of the comment to be added.
     * @param authentication current user information to be passed a JWT token in header.
     * @return CommentDetails including id of the comment created.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CommentDetails addComment(@PathVariable(value = "post_id") final Long postId,
                              @Valid @RequestBody AddCommentRequest addCommentRequest,
                              Authentication authentication){
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return commentService.addComment(postId,addCommentRequest,userContext.getId());
    }

    /**
     * The API  Edits a comment. Only comment authors can edit the comment.
     * User information has to be passed as a valid JWT Token in header.
     * @param updateCommentRequest details about the update on comment.
     * @param authentication current user information to be passed a JWT token in header.
     * @return CommentDetails - the updated comment's details.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    CommentDetails updateComment(@Valid @RequestBody UpdateCommentRequest updateCommentRequest,
                                 Authentication authentication){
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return  commentService.updateComment(updateCommentRequest,userContext.getId());
    }

    /**
     * The API fetch the details of the comment with given id
     * User information has to be passed as a valid JWT Token in header.
     * @param id id of the comment for which details has to be fetched
     * @return CommentDetails including comment information, created date and last updated date.
     */
    @GetMapping("{id}")
    CommentDetails getCommentDetails(@PathVariable("id") final Long id){
        return  commentService.getCommentDetails(id);
    }

    /**
     * API to delete a comment with given id.Only authors can delete a comment.
     * User information has to be passed as a valid JWT Token in header.
     * @param id id of the comment that has to be deleted
     * @param authentication current user information to be passed a JWT token in header
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteComment(@PathVariable("id") final Long id,
                       Authentication authentication){
        UserContext userContext = (UserContext) authentication.getPrincipal();
        commentService.deleteComment(id,userContext.getId());
    }

}