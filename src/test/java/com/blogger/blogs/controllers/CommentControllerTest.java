package com.blogger.blogs.controllers;
import com.blogger.blogs.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = { "/data.sql" })
public class CommentControllerTest extends IntegrationTest {

    private final String AUTHORIZATION_TOKEN_USER_100 ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDAiLCJlbWFpbCI6ImpvaG5Aam9obi5jb20ifQ.aSeBDVldL6u4Bz--CVQF2RWsCG9peOP63i5tPR2Sd7o";
    private final String AUTHORIZATION_TOKEN_USER_101 ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDEiLCJlbWFpbCI6ImpvaG5Aam9obi5jb20ifQ.jkswboFtquoFyIyeNn8ZqIsvG6JsZoMMZl-mr_FfUDY";

    @Test
    void listComments() throws Exception {
        mvc.perform((get("/posts/{postId}/comments", 1)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].comment", hasItem("Kilroy was here")));
    }

    @Test
    void getCommentDetails() throws Exception {
        mvc.perform((get("/posts/{postId}/comments/{commentId}", 1, 4)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment", equalTo("Kilroy was here")));
    }

    @Test
    void getNonExistingComment() throws Exception {
        mvc.perform((get("/posts/{postId}/comments/{commentId}", 1, 6666)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createComment() throws Exception {
        mvc.perform(
                        post("/posts/{postId}/comments", 1)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"comment\":\"my comment\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }

    @Test
    void addCommentForNonExistingPost() throws Exception {
        mvc.perform(
                        post("/posts/{postId}/comments", 7)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"comment\":\"my comment\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void updateComment() throws Exception {
        mvc.perform(
                        put("/posts/{postId}/comments", 1)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"5\",\"comment\":\"I am updating this comment\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(5)));
    }

    @Test
    void updateNonExistingComment() throws Exception {
        mvc.perform(
                        put("/posts/{postId}/comments", 1)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"1\",\"comment\":\"I am updating this comment\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void UnauthorizedUpdateComment() throws Exception {
        mvc.perform(
                        put("/posts/{postId}/comments", 1)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_101)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"5\",\"comment\":\"I am updating this comment\"}")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void commentTooLong() throws Exception {
        mvc.perform(
                        post("/posts/{postId}/comments", 1)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"comment\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void commentMissing() throws Exception {
        mvc.perform(
                        post("/posts/{postId}/comments", 1)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void deleteComment() throws Exception {
        mvc.perform(
                        delete("/posts/{postId}/comments/{commentId}", 1, 5)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                )
                .andExpect(status().isOk());
    }

    @Test
    void unauthorizedDeleteComment() throws Exception {
        mvc.perform(
                        delete("/posts/{postId}/comments/{commentId}", 1, 5)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_101)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteNonExistingComment() throws Exception {
        mvc.perform(
                        delete("/posts/{postId}/comments/{commentId}", 1, 9876)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                )
                .andExpect(status().isNotFound());
    }
}
