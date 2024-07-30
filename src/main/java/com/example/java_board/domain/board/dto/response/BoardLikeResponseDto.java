package com.example.java_board.domain.board.dto.response;

import com.example.java_board.domain.board.entity.BoardLike;

public class BoardLikeResponseDto {
    private Long id;

    public BoardLikeResponseDto() {}
    public BoardLikeResponseDto(BoardLike boardLike) {
        this.id = boardLike.getId();
    }
}
