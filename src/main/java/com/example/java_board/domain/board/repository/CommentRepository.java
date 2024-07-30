package com.example.java_board.domain.board.repository;

import com.example.java_board.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndBoardId(long id, long boardId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.parentComment.id = :parentId")
    int countChildComments(@Param("parentId") Long parentId);
}
