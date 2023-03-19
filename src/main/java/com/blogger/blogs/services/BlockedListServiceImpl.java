package com.blogger.blogs.services;

import com.blogger.blogs.entities.BlockedList;
import com.blogger.blogs.exceptions.DuplicateEntryException;
import com.blogger.blogs.exceptions.NotFoundException;
import com.blogger.blogs.exceptions.StatusCodes;
import com.blogger.blogs.repository.BlockedListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BlockedListServiceImpl implements BlockedListService {

    private final BlockedListRepository blockedListRepository;
    @Override
    public void blockUser(Long userId, Long blockedUserId) {
        List<BlockedList> blockedListsCurrentEntry = blockedListRepository.
                findAllByUserIdAndBlockedUserId(userId,blockedUserId);
        if(blockedListsCurrentEntry.size()>0){
            log.info("The user is already blocked");
            throw new DuplicateEntryException(StatusCodes.ALREADY_BLOCKED_USER.getStatusCode(),
                    StatusCodes.ALREADY_BLOCKED_USER.getStatusDescription());
        }
        BlockedList blockedList = new BlockedList();
        blockedList.setUserId(userId);
        blockedList.setBlockedUserId(blockedUserId);
        blockedListRepository.save(blockedList);
    }

    @Override
    public void unblockUser(Long userId, Long userIdToUnblock) {
        List<BlockedList> blockedListsCurrentEntry =blockedListRepository.findAllByUserIdAndBlockedUserId(userId,userIdToUnblock);
        if(blockedListsCurrentEntry.isEmpty()){
            log.info("User is not present in the blocked list");
            throw new NotFoundException(StatusCodes.USER_NOT_BLOCKED.getStatusCode(),
                    StatusCodes.USER_NOT_BLOCKED.getStatusDescription());
        }
        blockedListRepository.delete(blockedListsCurrentEntry.get(0));
    }
}