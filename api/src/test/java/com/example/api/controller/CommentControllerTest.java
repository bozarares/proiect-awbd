package com.example.api.controller;

import com.example.api.entity.Comment;
import com.example.api.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    public void setup() {
        // Configurarea unei autentificări mock pentru utilizator
        new User("user", "password", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetAllComments() throws Exception {
        // Setăm datele mock pentru comentarii
        Comment comment = new Comment();
        comment.setContent("Test comment");
        List<Comment> comments = Arrays.asList(comment);
        Page<Comment> page = new PageImpl<>(comments);
        when(commentService.getAllComments(any(PageRequest.class))).thenReturn(page);

        // Executăm cererea GET
        mockMvc.perform(get("/api/comments?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].content").value("Test comment"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testCreateComment() throws Exception {
        // Setăm datele mock pentru crearea unui comentariu
        Comment comment = new Comment();
        comment.setContent("New comment");
        when(commentService.createComment(any(Comment.class))).thenReturn(comment);

        // Executăm cererea POST
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\": \"New comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("New comment"));
    }
}
