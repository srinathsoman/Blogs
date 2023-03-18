package com.blogger.blogs.services;

import com.blogger.blogs.dto.CreatePostRequest;
import com.blogger.blogs.dto.PostDetails;
import com.blogger.blogs.dto.PostInfo;
import com.blogger.blogs.entities.Post;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    PostDetails createPost(@Valid CreatePostRequest createPostRequest);

    Page<PostInfo> getPosts(final Pageable pageable);

    PostDetails getPostDetails(Long id);

    void deletePost(Long id);

    Post getPostById(Long id);

}
