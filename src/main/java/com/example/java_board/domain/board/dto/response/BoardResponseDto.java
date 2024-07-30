package com.example.java_board.domain.board.dto.response;
import com.example.java_board.domain.board.dto.BoardLikeDto;
import com.example.java_board.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String userEmail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<BoardLikeDto> boardLikes;

    public BoardResponseDto() {}
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.userEmail = board.getUser_email();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }
}
