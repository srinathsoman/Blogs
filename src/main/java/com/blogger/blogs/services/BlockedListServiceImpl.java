package com.blogger.blogs.services;

import com.blogger.blogs.entities.BlockedList;
import com.blogger.blogs.repository.BlockedListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            return;
        }
        BlockedList blockedList = new BlockedList();
        blockedList.setUserId(userId);
        blockedList.setBlockedUserId(blockedUserId);
        blockedListRepository.save(blockedList);
    }

    @Override
    public void unblockUser(Long userId, Long userIdToUnblock) {
        List<BlockedList> blockedListsCurrentEntry =blockedListRepository.
                findAllByUserIdAndBlockedUserId(userId,userIdToUnblock);
        if(blockedListsCurrentEntry.isEmpty()){
            log.info("User is not present in the blocked list");
            return;
        }
        blockedListRepository.delete(blockedListsCurrentEntry.get(0));
    }

    @Override
    public List<Long> getBlockedUserIds(Long userId) {

        List<BlockedList> blockedUserList = blockedListRepository.findByUserId(userId);
        List<Long> blockedUserIds = blockedUserList.stream().
                map(BlockedList::getBlockedUserId).collect(Collectors.toList());
        return  blockedUserIds;
    }

    @Override
    public Boolean isUserBlocked(Long userId, Long checkBlockedId) {
        List<BlockedList> blockedListsCurrentEntry =blockedListRepository.
                findAllByUserIdAndBlockedUserId(userId,checkBlockedId);
        return !blockedListsCurrentEntry.isEmpty();
    }
}
