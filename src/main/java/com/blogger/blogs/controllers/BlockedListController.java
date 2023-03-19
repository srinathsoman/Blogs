package com.blogger.blogs.controllers;

import com.blogger.blogs.dto.UserContext;
import com.blogger.blogs.services.BlockedListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/posts/block/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlockedListController {

    private final BlockedListService blockedListService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void blockUser(@PathVariable(value = "id") final Long blockedUserId, Authentication authentication){
        UserContext userContext = (UserContext) authentication.getPrincipal();
        blockedListService.blockUser(userContext.getId(),blockedUserId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    void unblockUser(@PathVariable(value = "id") final Long userIdToUnblock, Authentication authentication){
        UserContext userContext = (UserContext) authentication.getPrincipal();
        blockedListService.unblockUser(userContext.getId(),userIdToUnblock);
    }
}
