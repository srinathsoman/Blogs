package com.blogger.blogs.services;

import com.blogger.blogs.dto.CreatePostRequest;
import com.blogger.blogs.dto.PostDetails;
import com.blogger.blogs.dto.PostInfo;
import com.blogger.blogs.entities.Post;
import com.blogger.blogs.exceptions.ExistingCommentException;
import com.blogger.blogs.exceptions.NotFoundException;
import com.blogger.blogs.exceptions.StatusCodes;
import com.blogger.blogs.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@Service
@Slf4j
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private  final ModelMapper modelMapper;


    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest) {

        Post post =modelMapper.map(createPostRequest, Post.class);
        post = postRepository.save(post);
        return modelMapper.map(post,PostDetails.class);
    }


    @Override
    public Page<PostInfo> getPosts(Pageable pageable) {


        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostInfo> postInfos = posts.map(new Function<Post, PostInfo>() {
            @Override
            public PostInfo apply(Post post) {
                return modelMapper.map(post,PostInfo.class);
            }
        });
        return postInfos;
    }



    @Override
    @ExceptionHandler(NotFoundException.class)
    public PostDetails getPostDetails(Long id)  {

        Optional<Post> post = postRepository.findById(id);
        if(!post.isPresent()){
            log.info("Post not found with id" + id);
            throw new NotFoundException(StatusCodes.POST_NOT_FOUND.getStatusCode(),
                    StatusCodes.POST_NOT_FOUND.getStatusDescription());
        }
        return modelMapper.map(post.get(),PostDetails.class);
    }

    @Override
    public void deletePost(Long id) {
        try {
            postRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
           // log.info("Post trying to be deleted not found with id : "+id);
            throw new NotFoundException(StatusCodes.POST_TO_DELETE_NOT_FOUND.getStatusCode(),
                    StatusCodes.POST_TO_DELETE_NOT_FOUND.getStatusDescription());
        }
        catch (IllegalArgumentException e){
           // log.error("One or more comment exist for the Post. Hence cannot delete");
            throw new ExistingCommentException(StatusCodes.COMMENT_EXISTS_FOR_POST.getStatusCode(),
                    StatusCodes.COMMENT_EXISTS_FOR_POST.getStatusDescription());
        }
    }

    @Override
    @ExceptionHandler(NotFoundException.class)
    public Post getPostById(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(!post.isPresent()){
            log.info("Post not found with id" + id);
            throw new NotFoundException("5002","Post with id "+id+" is not available");
        }
        return post.get();
    }

}