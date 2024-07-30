package com.example.java_board.domain.board.service;

import com.example.java_board.domain.board.dto.request.CreateBoardDto;
import com.example.java_board.domain.board.dto.request.CreateCommentDto;
import com.example.java_board.domain.board.dto.request.UpdateBoardDto;
import com.example.java_board.domain.board.dto.response.BoardResponseDto;
import com.example.java_board.domain.board.dto.response.CommentResponseDto;
import com.example.java_board.domain.board.entity.Board;
import com.example.java_board.domain.board.entity.BoardLike;
import com.example.java_board.domain.board.entity.Comment;
import com.example.java_board.domain.board.repository.BoardLikeRepository;
import com.example.java_board.domain.board.repository.BoardRepository;
import com.example.java_board.domain.board.repository.CommentLikeRepository;
import com.example.java_board.domain.board.repository.CommentRepository;
import com.example.java_board.domain.user.entity.User;
import com.example.java_board.domain.user.repository.UserRepository;
import org.assertj.core.api.StandardSoftAssertionsProvider;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.dnd.DragSourceMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import java.lang.reflect.Field;

@ExtendWith(SpringExtension.class)
class BoardServiceTest {

    // 테스트 주체
    BoardService boardService;

    // 협력자 객체 주입
    @MockBean
    BoardRepository boardRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CommentRepository commentRepository;

    @MockBean
    CommentLikeRepository commentLikeRepository;

    @MockBean
    BoardLikeRepository boardLikeRepository;

    // 테스트 메서드간 독립성 유지(이전 메서드에서 변형된 객체가 다음 테스트전에 다시 초기화 되도록)
    @BeforeEach
    void setUp() {
        boardService = new BoardService(
                boardRepository = boardRepository,
                userRepository = userRepository,
                commentRepository = commentRepository,
                boardLikeRepository = boardLikeRepository,
                commentLikeRepository = commentLikeRepository);
    }

    // mock data
    long userId = 1L;
    long boardId = 8L;

    long boardId1 = 1L;
    long boardId2 = 2L;
    long boardId3 = 3L;

    long notExistingUserId = 10L;
    long notExistingBoardId = 10L;
    long notExistingCommentId = 10L;

    CreateBoardDto createBoardDto1 = new CreateBoardDto(userId,"title_1", "content_1");
    CreateBoardDto createBoardDto2 = new CreateBoardDto(userId,"title_2", "content_2");
    CreateBoardDto createBoardDto3 = new CreateBoardDto(userId,"title_3", "content_3");

    UpdateBoardDto updateBoardDto = new UpdateBoardDto("updated title", "updated content");


    long userId1 = 1L;
    long userId2 = 2L;
    long userId3 = 3L;

    User user1 = new User(); // User 객체 생성
    User user2 = new User(); // User 객체 생성
    User user3 = new User(); // User 객체 생성

    Board board1 = new Board(user1, createBoardDto1);
    Board board2 = new Board(user2, createBoardDto2);
    Board board3 = new Board(user3, createBoardDto3);

    CreateCommentDto createCommentDto1 = new CreateCommentDto(userId1,"comment1");
    CreateCommentDto createCommentDto2 = new CreateCommentDto(userId2,"comment2");
    CreateCommentDto createCommentDto3 = new CreateCommentDto(userId3,"comment3");

    CreateCommentDto createChildCommentDto = new CreateCommentDto(userId1, "child comment");

    long commentId1 = 1L;
    long commentId2 = 2L;
    long commentId3 = 3L;

    Comment comment1 = new Comment(user1, board1, createCommentDto1);
    Comment comment2 = new Comment(user2, board2, createCommentDto2);
    Comment comment3 = new Comment(user3, board3, createCommentDto3);

    Comment childComment1 = new Comment(user1, board1, comment1, createChildCommentDto);

    // Create
    @Test
    void testCreateBoard_UserNotFound() {
        // given

        // when(stubbing)
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> {
            boardService.createBoard(createBoardDto1);
        });
    }

    @Test
    void testCreateBoard_Success() {
        // given
        Board board = new Board(user1, createBoardDto1); // Board 객체 생성

        // when
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        Mockito.when(boardRepository.save(any(Board.class))).thenReturn(board);

        BoardResponseDto newBoardId = boardService.createBoard(createBoardDto1);

        // then
        assertThat(newBoardId.getTitle()).isEqualTo(createBoardDto1.title());
        assertThat(newBoardId.getContent()).isEqualTo(createBoardDto1.content());
    }

    //Read
    @Test
    void testFindAllBoardList_Success(){
        // Given
        List<Board> boardList = Arrays.asList(board1, board2, board3);

        // when
        Mockito.when(boardRepository.findAll()).thenReturn(boardList);
        List<BoardResponseDto> result= boardService.findAllBoardList();

        // then
        assertEquals(3, result.size());
        assertEquals(board1.getTitle(),result.get(0).getTitle());
        assertEquals(board2.getTitle(),result.get(1).getTitle());
        assertEquals(board3.getTitle(),result.get(2).getTitle());
    }

    @Test
    void testFindBoard_Success(){
        // Given

        // when
        Mockito.when(boardRepository.findById(boardId)).thenReturn(Optional.of(board1));
        BoardResponseDto result= boardService.findBoard(boardId);

        // then
        assertEquals(board1.getTitle(),result.getTitle());
    }

    @Test
    void testFindBoard_ExistingBoardNotFound(){
        // given

        // when
        Mockito.when(boardRepository.findById(notExistingBoardId)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> {
            boardService.findBoard(notExistingBoardId);
        });
    }

    // Update
    @Test
    void testUpdatedBoard_ExistingBoardNotFound(){
        // given

        // when
        Mockito.when(boardRepository.findById(notExistingBoardId)).thenReturn(Optional.empty());

        // Then
        assertThrows(RuntimeException.class, ()->{
            boardService.updateBoard(notExistingBoardId,updateBoardDto);
        });
    }

    @Test
    void testUpdatedBoard_Success(){
        // given
        Board updatedBoard = board1;
        updatedBoard.update(updateBoardDto);
        // when
        Mockito.when(boardRepository.findById(boardId1)).thenReturn(Optional.of(board1));
        Mockito.when(boardRepository.save(updatedBoard)).thenReturn(updatedBoard);

        BoardResponseDto updatedBoardResponseDto = boardService.updateBoard(boardId1,updateBoardDto);

        // then
        assertEquals(updateBoardDto.title(),updatedBoardResponseDto.getTitle());
        assertEquals(updateBoardDto.content(),updatedBoardResponseDto.getContent());
    }

    // Delete
    @Test
    void testDeleteBoard_ExistingBoardNotFound() {
        // given

        // when
        Mockito.when(boardRepository.findById(notExistingBoardId)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> {
            boardService.deleteBoard(notExistingBoardId);
        });
    }

    @Test
    void testDeleteBoard_Success() {
        // given

        // when
        Mockito.when(boardRepository.findById(boardId1)).thenReturn(Optional.of(board1));
        boardService.deleteBoard(boardId1);

        // then
        verify(boardRepository).deleteById(boardId1);
    }

    // Create

    @Test
    void testCreateComment_UserNotFound() {
        // given

        // when
        Mockito.when(userRepository.findById(notExistingUserId)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> {
            boardService.createComment(boardId, createCommentDto1);
        });
    }

    @Test
    void testCreateComment_ExistingBoardNotFound() {
        // given

        // when
        Mockito.when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        Mockito.when(boardRepository.findById(notExistingBoardId)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> {
            boardService.createComment(boardId, createCommentDto1);
        });
    }

    @Test
    void testCreateComment_Success() {
        // given

         // when
         Mockito.when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
         Mockito.when(boardRepository.findById(boardId1)).thenReturn(Optional.of(board1));
         Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(comment1);

         CommentResponseDto commentResponseDto = boardService.createComment(boardId1, createCommentDto1);

         // then
         assertThat(commentResponseDto.getContent()).isEqualTo(createCommentDto1.content());
    }

    @Test
    void testChildComment_NotExistingParentComment(){
        // given

        // when
        Mockito.when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        Mockito.when(boardRepository.findById(boardId1)).thenReturn(Optional.of(board1));
        Mockito.when(commentRepository.findById(notExistingCommentId)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> {
            boardService.createChildComment(boardId, notExistingCommentId, createCommentDto1);
        });
    }

    @Test
    void testChildComment_Success() {
        // given

        // when
        Mockito.when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        Mockito.when(boardRepository.findById(boardId1)).thenReturn(Optional.of(board1));
        Mockito.when(commentRepository.findByIdAndBoardId(commentId1,boardId1)).thenReturn(Optional.of(comment1));
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(childComment1);

        CommentResponseDto newChildCommentResponseDto = boardService.createChildComment(boardId1, commentId1, createChildCommentDto);

        // then
        // user1(id=1)이 해당 comment를 작성한것 처럼 mocking하고 싶으나, autogenerated는 실제 db에 저장될때 실행됨.
        // 따라서, 테스트시에는 db까지 갈 일이 없어서, user1 ~ user3까지의 모든 id=0이됨.
//        assertEquals(userId1, newChildCommentResponseDto.getUserId());
        assertEquals(user1.getId(),newChildCommentResponseDto.getUserId());
        assertEquals(board1.getId(),newChildCommentResponseDto.getBoardId());
        assertEquals(createChildCommentDto.content(),newChildCommentResponseDto.getContent());
    }

    @Test
    @Disabled
    void testDeleteComment_WithChildComment() {

    }

}