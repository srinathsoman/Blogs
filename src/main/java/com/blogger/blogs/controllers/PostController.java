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
     * The API fetches the paginated list of all Blog Posts
     * @param pageable
     * @return Page<PostInfo>
     */
    @GetMapping
    Page<PostInfo> getPosts(Pageable pageable, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.getPosts(pageable,userContext.getId());
    }

    /**
     * API used to create a new blog post with given details
     * @param createPostRequest
     * @return PostDetails
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDetails createPost(@Valid @RequestBody CreatePostRequest createPostRequest, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.createPost(createPostRequest,userContext.getId());
    }

    /**
     * API to fetch the details of a post with given id
     * @param id
     * @return PostDetails
     */
    @GetMapping("{id}")
    PostDetails getPostDetails(@PathVariable("id") final Long id, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.getPostDetails(id,userContext.getId());
    }

    /**
     * API to delete a post with a given id
     * @param id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePost(@PathVariable("id") final Long id, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        postService.deletePost(id,userContext.getId());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    PostDetails updatePost(@Valid @RequestBody UpdatePostRequest updatePostRequest, Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return postService.updatePost(updatePostRequest,userContext.getId());
    }
}
