package com.example.java_board.domain.board.dto.request;

import com.example.java_board.domain.board.entity.Board;
import com.example.java_board.domain.board.entity.Comment;
import com.example.java_board.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

public record CreateCommentDto(
        @NotNull
        long user_id,
        @NotNull
        String content
){
    public Comment toEntity(User user, Board board){
        return new Comment(user, board, this);
    }
}
