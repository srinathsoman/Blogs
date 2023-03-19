package com.blogger.blogs.services;

import com.blogger.blogs.dto.CreatePostRequest;
import com.blogger.blogs.dto.PostDetails;
import com.blogger.blogs.dto.PostInfo;
import com.blogger.blogs.dto.UpdatePostRequest;
import com.blogger.blogs.entities.Post;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    PostDetails createPost(@Valid CreatePostRequest createPostRequest,Long userId);

    Page<PostInfo> getPosts(final Pageable pageable);

    PostDetails getPostDetails(Long id);

    void deletePost(Long id, Long userId);

    Post getPostById(Long id);

    PostDetails updatePost(@Valid UpdatePostRequest updatePostRequest, Long userId);

}
