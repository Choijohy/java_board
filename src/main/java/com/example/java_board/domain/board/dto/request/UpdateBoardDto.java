package com.example.java_board.domain.board.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateBoardDto(
        @NotNull
        String title,
        String content)
{
}
