package com.blogger.blogs.services;

public interface BlockedListService {

    void blockUser(Long userId, Long blockedUserId);

    void unblockUser(Long userId, Long userIdToUnblock);
}
