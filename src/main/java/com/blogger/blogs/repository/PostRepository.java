package com.blogger.blogs.repository;

import com.blogger.blogs.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserIdNotIn(List<Long> blockedUserIds, Pageable pageable);
}
