package com.example.java_board.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardLikeDto {
    private Long id;
    private Long userId;
    private Long boarId;
}
