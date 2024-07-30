package com.example.java_board.domain.board.controller;

import com.example.java_board.domain.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BoardController.class) // 지정된 class(BoardController)를 로드하여, 테스트를 수행할 수 있도록함 cf. @SpringBootTest은 모든 빈을 로드한다(불필요)
public class BoardControllerTest {

    @Autowired // 자동 주입
    private MockMvc mvc;

    @MockBean // 서비스 객체 의존성 주입
    BoardService boardService;

    @Test
    @DisplayName("controller - createBoard")
    void createBoard() {

    }

    @Test
    @DisplayName("controller - updateBoard")
    void updateBoard() {
    }

    @Test
    @DisplayName("controller - deleteBoardById")
    void deleteBoardById() {
    }

    @Test
    @DisplayName("controller - createComment")
    void createComment() {
    }

    @Test
    @DisplayName("controller - updateComment")
    void updateComment() {
    }

    @Test
    void createChildComment() {
    }

    @Test
    void deleteComment() {
    }

    @Test
    void createBoardLike() {
    }

    @Test
    void deleteBoardLike() {
    }

    @Test
    void createCommentLike() {
    }

    @Test
    void deleteCommentLike() {
    }

    @Test
    void getBoardById() {
    }
}
