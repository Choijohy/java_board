package com.example.java_board.domain.board.dto.request;
import com.example.java_board.domain.board.entity.Board;
import com.example.java_board.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;


public record CreateBoardDto(
        @NotNull
        long userId,
        @NotNull
        String title,
        @NotNull
        String content)
{
    public Board toEntity(User user){
        return new Board(user, this);
    }


}


