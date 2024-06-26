package com.example.api.service;

import com.example.api.entity.Comment;
import com.example.api.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    private Comment comment;

    @BeforeEach
    public void setUp() {
        comment = new Comment();
        comment.setContent("Test comment");
    }

    @Test
    public void testGetAllComments() {
        // Setăm datele mock
        List<Comment> comments = Arrays.asList(comment);
        Page<Comment> page = new PageImpl<>(comments);
        when(commentRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Executăm metoda getAllComments
        Page<Comment> result = commentService.getAllComments(PageRequest.of(0, 5));

        // Verificăm răspunsul
        assertEquals(1, result.getTotalElements());
        assertEquals("Test comment", result.getContent().get(0).getContent());
    }

    @Test
    public void testCreateComment() {
        // Setăm datele mock
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Executăm createComment
        Comment result = commentService.createComment(comment);

        // Verificăm răspunsul
        assertEquals("Test comment", result.getContent());
    }
}
