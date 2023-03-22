package com.blogger.blogs.controllers;
import com.blogger.blogs.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = { "/data.sql" })
public class PostControllerTest extends IntegrationTest {

    private final String AUTHORIZATION_TOKEN_USER_100 ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDAiLCJlbWFpbCI6ImpvaG5Aam9obi5jb20ifQ.aSeBDVldL6u4Bz--CVQF2RWsCG9peOP63i5tPR2Sd7o";
    private final String AUTHORIZATION_TOKEN_USER_101 ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDEiLCJlbWFpbCI6ImpvaG5Aam9obi5jb20ifQ.jkswboFtquoFyIyeNn8ZqIsvG6JsZoMMZl-mr_FfUDY";

    @Test
    void listPosts() throws Exception {
        mvc.perform((get("/posts")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].title", hasItem("Welcome")));
    }

    @Test
    void listPostsExcludingBlocked() throws Exception {
        mvc.perform((get("/posts")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_101)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].title", is(not(hasItem("Welcome")))));
    }

    @Test
    void getPostDetails() throws Exception {
        mvc.perform((get("/posts/1")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Welcome")));
    }

    @Test
    void getPostDetailsOfBlockedUser() throws Exception {
        mvc.perform((get("/posts/1")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_101)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getNonExistingPost() throws Exception {
        mvc.perform((get("/posts/7")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPost() throws Exception {
        mvc.perform(
                        post("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"new post\",\"content\":\"new content\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }
    @Test
    void editPost() throws Exception{
        mvc.perform(
                        put("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"2\",\"title\":\"updated title\",\"content\":\"updated content\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(2)));
    }

    @Test
    void editNonExistingPost() throws Exception{
        mvc.perform(
                        put("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"7\",\"title\":\"updated title\",\"content\":\"updated content\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void unauthorizedEditPost() throws Exception{
        mvc.perform(
                        put("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_101)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"2\",\"title\":\"updated title\",\"content\":\"updated content\"}")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void titleTooLong() throws Exception {
        mvc.perform(
                        post("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                                        "\",\"content\":\"new content\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void titleAndContentMissing() throws Exception {
        mvc.perform(
                        post("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void contentMissing() throws Exception {
        mvc.perform(
                        post("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"something missing\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePost() throws Exception {
        mvc.perform(
                        delete("/posts/2")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                )
                .andExpect(status().isOk());
    }

    @Test
    void deletePostWithComments() throws Exception {
        mvc.perform(
                        delete("/posts/1")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void deleteNonExistingPost() throws Exception {
        mvc.perform(
                        delete("/posts/9876")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void unauthorizedDeletePost() throws Exception {
        mvc.perform(
                        delete("/posts/2")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_101)
                )
                .andExpect(status().isUnauthorized());
    }
}
