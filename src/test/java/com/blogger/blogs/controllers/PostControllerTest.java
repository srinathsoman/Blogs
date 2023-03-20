package com.blogger.blogs.controllers;
import com.blogger.blogs.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PostControllerTest extends IntegrationTest {

    private final String AUTHORIZATION_TOKEN_USER_100 ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDAiLCJlbWFpbCI6ImpvaG5Aam9obi5jb20ifQ.aSeBDVldL6u4Bz--CVQF2RWsCG9peOP63i5tPR2Sd7o";

    /*@BeforeTestClass
    @Sql({"/data.sql"})
    void initializeData(){

    }*/

    @Test
    void listPosts() throws Exception {
        mvc.perform((get("/posts")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].title", hasItem("Welcome")));
    }

    @Test
    void getPostDetails() throws Exception {
        mvc.perform((get("/posts/1")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Welcome")));
    }

    @Test
    void getNonExistingPost() throws Exception {
        mvc.perform((get("/posts/666")
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
    void titleTooLong() throws Exception {
        mvc.perform(
                        post("/posts")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\",\"content\":\"new content\"}")
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
    void deleteNonExistingPost() throws Exception {
        mvc.perform(
                        delete("/posts/9876")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+AUTHORIZATION_TOKEN_USER_100)
                )
                .andExpect(status().isNotFound());
    }
}
