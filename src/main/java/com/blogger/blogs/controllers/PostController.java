package com.blogger.blogs.controllers;


import com.blogger.blogs.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import com.blogger.blogs.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides REST APIs associated with operations on Post.
 *
 * @author Srinath
 */
@RestController
@RequestMapping(path = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    /**
     * The API fetches the paginated list of all Blog Posts. The system excludes posts authored by blocked users.
     * User information has to be passed as a valid JWT Token in header.
     * @param pageable Paging information
     * @param authentication current user information to be passed a JWT token in header
     * @return A paginated list of all posts
     */
    @GetMapping
    Page<PostInfo> getPosts(Pageable pageable, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.getPosts(pageable,userContext.getId());
    }

    /**
     * API used to create a new blog post with given details.A Valid authentication is required.
     * User information has to be passed as a valid JWT Token in header.
     * @param createPostRequest The details for the post to be created.
     * @param authentication current user information to be passed a JWT token in header
     * @return PostDetails - the details of created post including id.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDetails createPost(@Valid @RequestBody CreatePostRequest createPostRequest, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.createPost(createPostRequest,userContext.getId());
    }

    /**
     * API to fetch the details of a post with given id.
     * User information has to be passed as a valid JWT Token in header.
     * @param id The id of post for which details has to be fetched.
     * @param authentication current user information to be passed a JWT token in header
     * @return PostDetails
     * @throws org.springframework.web.client.HttpClientErrorException.Forbidden if the requested post is authored by a blocked user.
     */
    @GetMapping("{id}")
    PostDetails getPostDetails(@PathVariable("id") final Long id, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.getPostDetails(id,userContext.getId());
    }

    /**
     * API to delete a post with a given id. Only owners can delete a post.
     * User information has to be passed as a valid JWT Token in header.
     * @param id the id of the post to be deleted.
     * @param authentication current user information to be passed a JWT token in header
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePost(@PathVariable("id") final Long id, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        postService.deletePost(id,userContext.getId());
    }

    /**
     * API to edit a post with a given id. Only owners can edit a post.
     * User information has to be passed as a valid JWT Token in header.
     * @param updatePostRequest A json with the updated information about the post.
     * @param authentication current user information to be passed a JWT token in header
     * @return PostDetails- details of the updated post.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    PostDetails updatePost(@Valid @RequestBody UpdatePostRequest updatePostRequest, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.updatePost(updatePostRequest,userContext.getId());
    }
}
