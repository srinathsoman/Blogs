package com.blogger.blogs.services;

import com.blogger.blogs.dto.CreatePostRequest;
import com.blogger.blogs.dto.PostDetails;
import com.blogger.blogs.dto.PostInfo;
import com.blogger.blogs.dto.UpdatePostRequest;
import com.blogger.blogs.entities.Post;
import com.blogger.blogs.exceptions.ExistingCommentException;
import com.blogger.blogs.exceptions.NotFoundException;
import com.blogger.blogs.exceptions.StatusCodes;
import com.blogger.blogs.exceptions.UnauthorizedModificationException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@Service
@Slf4j
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private  final ModelMapper modelMapper;

    private final BlockedListService blockedListService;


    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest,Long userId) {

        Post post =modelMapper.map(createPostRequest, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        return modelMapper.map(post,PostDetails.class);
    }


    @Override
    public Page<PostInfo> getPosts(Pageable pageable, Long userId) {


        Page<Post> posts = postRepository.findAll(pageable);
        List<Long> blockedUserIds =blockedListService.getBlockedUserIds(userId);
        posts=postRepository.findAllByUserIdNotIn(blockedUserIds,pageable);
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
    public void deletePost(Long id, Long userId) {

        log.debug("Deleting post with id: "+id);
        Optional<Post> postToDelete = postRepository.findById(id);
        if (!postToDelete.isPresent()){
            log.info("Post trying to be deleted not found with id : "+id);
            throw new NotFoundException(StatusCodes.POST_TO_DELETE_NOT_FOUND.getStatusCode(),
                    StatusCodes.POST_TO_DELETE_NOT_FOUND.getStatusDescription());
        }
        if(postToDelete.get().getUserId()!=userId){
            log.info("The post trying to be deleted is not owned by the current user. UserId->" + userId);
            throw new UnauthorizedModificationException(StatusCodes.UNAUTHORIZED_DELETE_POST.getStatusCode(),
                    StatusCodes.UNAUTHORIZED_DELETE_POST.getStatusDescription());
        }
        if(!postToDelete.get().getComments().isEmpty()){
            log.error("One or more comment exist for the Post. Hence cannot delete");
            throw new ExistingCommentException(StatusCodes.COMMENT_EXISTS_FOR_POST.getStatusCode(),
                    StatusCodes.COMMENT_EXISTS_FOR_POST.getStatusDescription());
        }
        postRepository.deleteById(id);
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

    @Override
    public PostDetails updatePost(UpdatePostRequest updatePostRequest, Long userId) {

        Optional<Post> existingPost = postRepository.findById(updatePostRequest.getId());
        if(!existingPost.isPresent()){
            log.info("Post trying to be updated not found id->" + updatePostRequest.getId());
            throw new NotFoundException(StatusCodes.POST_NOT_FOUND.getStatusCode(),
                    StatusCodes.POST_NOT_FOUND.getStatusDescription());
        }
        if(existingPost.get().getUserId()!=userId){
            log.info("The post trying to be modified is not owned by the current user. UserId->" + userId);
            throw new UnauthorizedModificationException(StatusCodes.UNAUTHORIZED_EDIT_POST.getStatusCode(),
                    StatusCodes.UNAUTHORIZED_EDIT_POST.getStatusDescription());
        }
        Post updatedPost = existingPost.get();
        updatedPost.setTitle(updatePostRequest.getTitle());
        updatedPost.setContent(updatePostRequest.getContent());
        updatedPost = postRepository.save(updatedPost);
        return  modelMapper.map(updatedPost, PostDetails.class);
    }

}
