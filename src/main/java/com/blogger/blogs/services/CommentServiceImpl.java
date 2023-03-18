package com.blogger.blogs.services;

import com.blogger.blogs.dto.AddCommentRequest;
import com.blogger.blogs.dto.CommentDetails;
import com.blogger.blogs.dto.CommentInfo;
import com.blogger.blogs.entities.Comment;
import com.blogger.blogs.exceptions.NotFoundException;
import com.blogger.blogs.exceptions.StatusCodes;
import com.blogger.blogs.repository.CommentRepository;
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

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final PostService postService;

    @Override
    public CommentDetails addComment(Long postId, @Valid AddCommentRequest addCommentRequest) {

        Comment comment = modelMapper.map(addCommentRequest,Comment.class);
        comment.setPost(postService.getPostById(postId));
        comment =  commentRepository.save(comment);
        return modelMapper.map(comment,CommentDetails.class);
    }

    @Override
    public Page<CommentInfo> getComments(Long postId, Pageable pageable) {

        Page<Comment> comments = commentRepository.findAllByPostId(postId,pageable);
        Page<CommentInfo> commentInfos = comments.map(new Function<Comment, CommentInfo>() {
            @Override
            public CommentInfo apply(Comment comment) {
                return modelMapper.map(comment,CommentInfo.class);
            }
        });
        return  commentInfos;

    }

    @Override
    @ExceptionHandler(NotFoundException.class)
    public CommentDetails getCommentDetails(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            log.info("Comment not found with id  :"+id);
            throw new NotFoundException(StatusCodes.COMMENT_NOT_FOUND.getStatusCode(),
                    StatusCodes.COMMENT_NOT_FOUND.getStatusDescription());
        }
        return modelMapper.map(comment.get(),CommentDetails.class);
    }

    @Override
    @ExceptionHandler(NotFoundException.class)
    public void deleteComment(Long id) {

        log.debug("Deleting comment with id"+id);
        Optional<Comment> commentToDelete= commentRepository.findById(id);
        if(!commentToDelete.isPresent()){
            log.info("Comment trying to be deleted not found . Comment id : " + id);
            throw new NotFoundException(StatusCodes.COMMENT_TO_DELETE_NOT_FOUND.getStatusCode(),
                    StatusCodes.COMMENT_TO_DELETE_NOT_FOUND.getStatusDescription());
            }
        commentRepository.delete(commentToDelete.get());
    }
}
