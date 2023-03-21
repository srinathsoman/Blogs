package com.blogger.blogs.controllers;

import com.blogger.blogs.dto.UserContext;
import com.blogger.blogs.services.BlockedListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Class having rest APIs for blocking/unblocking of specific users
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "/posts/block/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlockedListController {

    private final BlockedListService blockedListService;

    /**
     * API to block a user.
     * The current users details are fetched from the authentication header.
     * The id of user to be blocked has to be passed as a path variable
     * @param blockedUserId the id of user to be blocked
     * @param authentication current user information to be passed as JWT token in header
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void blockUser(@PathVariable(value = "id") final Long blockedUserId, Authentication authentication){
        UserContext userContext = (UserContext) authentication.getPrincipal();
        blockedListService.blockUser(userContext.getId(),blockedUserId);
    }

    /**
     * API to unblock a user.
     * The current users details are fetched from the authentication header.
     * The id of user to be blocked has to be passed as a path variable
     * @param userIdToUnblock the id of user to be unblocked
     * @param authentication current user information to be passed a JWT token in header
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    void unblockUser(@PathVariable(value = "id") final Long userIdToUnblock, Authentication authentication){
        UserContext userContext = (UserContext) authentication.getPrincipal();
        blockedListService.unblockUser(userContext.getId(),userIdToUnblock);
    }
}
