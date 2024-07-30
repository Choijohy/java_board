package com.example.java_board.domain.board.repository;

import com.example.java_board.domain.board.entity.Board;
import com.example.java_board.domain.board.entity.BoardLike;
import com.example.java_board.domain.board.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardIdAndUserId(Long boardId, Long userId);
}
