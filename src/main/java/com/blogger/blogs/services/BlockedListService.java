package com.blogger.blogs.services;

import java.util.List;

public interface BlockedListService {

    void blockUser(Long userId, Long blockedUserId);

    void unblockUser(Long userId, Long userIdToUnblock);

    List<Long> getBlockedUserIds(Long userId);
}
