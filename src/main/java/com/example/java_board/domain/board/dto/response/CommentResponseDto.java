package com.example.java_board.domain.board.dto.response;

import com.example.java_board.domain.board.dto.BoardLikeDto;
import com.example.java_board.domain.board.entity.Board;
import com.example.java_board.domain.board.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {
    private long id;
    private long userId;
    private long boardId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(){};
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.boardId = comment.getBoard().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

}
