package com.blogger.blogs.controllers;

import com.blogger.blogs.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BlockedListControllerTest extends IntegrationTest {

    private final String AUTHORIZATION_TOKEN_USER_100 ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDAiLCJlbWFpbCI6ImpvaG5Aam9obi5jb20ifQ.aSeBDVldL6u4Bz--CVQF2RWsCG9peOP63i5tPR2Sd7o";

    @Test
    void blockUser() throws Exception {
        mvc.perform((post("/posts/block/{id}", 105)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isCreated());
    }

    @Test
    void unblockUser() throws Exception {
        mvc.perform((delete("/posts/block/{id}", 105)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isOk());
    }
}
