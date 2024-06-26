package com.example.api.controller;

import com.example.api.entity.Comment;
import com.example.api.service.CommentService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    /**
     * Endpoint pentru a obține toate comentariile cu paginare.
     * 
     * @param page Indexul paginii (implicit 0).
     * @param size Dimensiunea paginii (implicit 5).
     * @return O pagină de comentarii sortată după data creării în ordine
     *         descrescătoare.
     */
    @GetMapping
    public Page<Comment> getAllComments(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        logger.info("Fetching all comments, page: {}, size: {}", page, size);
        return commentService.getAllComments(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")));
    }

    /**
     * Endpoint pentru a obține un comentariu după ID.
     * 
     * @param id ID-ul comentariului.
     * @return Comentariul găsit.
     */
    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        logger.info("Fetching comment with ID: {}", id);
        return commentService.getCommentById(id);
    }

    /**
     * Endpoint pentru a obține comentariile asociate unui task cu paginare.
     * 
     * @param taskId ID-ul task-ului.
     * @param page   Indexul paginii (implicit 0).
     * @param size   Dimensiunea paginii (implicit 5).
     * @return O pagină de comentarii sortată după ID în ordine descrescătoare.
     */
    @GetMapping("/task/{taskId}")
    public Page<Comment> getTaskComments(@PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        logger.info("Fetching comments for task ID: {}, page: {}, size: {}", taskId, page, size);
        return commentService.getTaskComments(taskId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
    }

    /**
     * Endpoint pentru a crea un nou comentariu.
     * 
     * @param comment Obiectul Comment ce trebuie creat.
     * @return Comentariul creat.
     */
    @PostMapping
    public Comment createComment(@RequestBody @Valid Comment comment) {
        logger.info("Creating new comment: {}", comment.getContent());
        return commentService.createComment(comment);
    }

    /**
     * Endpoint pentru a actualiza un comentariu existent.
     * 
     * @param id             ID-ul comentariului ce trebuie actualizat.
     * @param commentDetails Detaliile actualizate ale comentariului.
     * @return Comentariul actualizat.
     */
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody @Valid Comment commentDetails) {
        logger.info("Updating comment with ID: {}", id);
        return commentService.updateComment(id, commentDetails);
    }

    /**
     * Endpoint pentru a șterge un comentariu după ID.
     * 
     * @param id ID-ul comentariului ce trebuie șters.
     */
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        logger.info("Deleting comment with ID: {}", id);
        commentService.deleteComment(id);
    }
}
