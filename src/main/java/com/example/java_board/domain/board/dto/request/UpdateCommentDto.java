package com.example.java_board.domain.board.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCommentDto (
        @NotNull
        long commentId,
        @NotNull
        String content
){
}
