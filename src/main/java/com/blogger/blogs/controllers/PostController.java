package com.blogger.blogs.controllers;


import com.blogger.blogs.dto.CreatePostRequest;
import com.blogger.blogs.dto.PostDetails;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import com.blogger.blogs.dto.PostInfo;
import com.blogger.blogs.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    Page<PostInfo> getPosts(Pageable pageable) {
        return postService.getPosts(pageable);
    }

    /**
     * API used to create a new blog post with given details
     * @param createPostRequest
     * @return PostDetails
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDetails createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    /**
     * API to fetch the details of a post with given id
     * @param id
     * @return PostDetails
     */
    @GetMapping("{id}")
    PostDetails getPostDetails(@PathVariable("id") final Long id) {
        return postService.getPostDetails(id);
    }

    /**
     * API to delete a post with a given id
     * @param id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePost(@PathVariable("id") final Long id) {
        postService.deletePost(id);
    }
}
