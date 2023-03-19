package com.blogger.blogs.repository;

import com.blogger.blogs.entities.BlockedList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockedListRepository extends JpaRepository<BlockedList, Long> {

    List<BlockedList> findAllByUserIdAndBlockedUserId(Long userId, Long blockedUserId);

    List<BlockedList> findByUserId(Long userId);
}
