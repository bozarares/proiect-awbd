package com.example.api.repository;

import com.example.api.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Metodă pentru a găsi comentariile după ID-ul task-ului, cu paginare
    Page<Comment> findByTaskId(Long taskId, Pageable pageable);
}
