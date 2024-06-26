package com.example.api.service;

import com.example.api.entity.Comment;
import com.example.api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Obține toate comentariile cu paginare
    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    // Obține un comentariu după ID
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    // Obține comentariile asociate unui task specific, cu paginare
    public Page<Comment> getTaskComments(Long taskId, Pageable pageable) {
        return commentRepository.findByTaskId(taskId, pageable);
    }

    // Creează un nou comentariu
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Actualizează un comentariu existent
    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setContent(commentDetails.getContent());
            comment.setCreatedDate(commentDetails.getCreatedDate());
            comment.setUser(commentDetails.getUser());
            comment.setTask(commentDetails.getTask());
            return commentRepository.save(comment);
        }
        return null;
    }

    // Șterge un comentariu după ID
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
