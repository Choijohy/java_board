package com.example.java_board.domain.board.controller;


import com.example.java_board.domain.board.dto.request.CreateBoardDto;
import com.example.java_board.domain.board.dto.request.CreateCommentDto;
import com.example.java_board.domain.board.dto.request.UpdateBoardDto;
import com.example.java_board.domain.board.dto.request.UpdateCommentDto;
import com.example.java_board.domain.board.dto.response.BoardLikeResponseDto;
import com.example.java_board.domain.board.dto.response.BoardResponseDto;
import com.example.java_board.domain.board.dto.response.CommentResponseDto;
import com.example.java_board.domain.board.entity.Board;
import com.example.java_board.domain.board.service.BoardService;
import com.example.java_board.global.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(@RequestBody CreateBoardDto createBoardDto) {
        BoardResponseDto data = boardService.createBoard(createBoardDto);
        ApiResponse<BoardResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, "Board created successfully",data);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 전체 게시물 조회
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getBoardList() {
        List<BoardResponseDto> data = boardService.findAllBoardList();
        ApiResponse<List<BoardResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "All Boards get successfully",data);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 특정 게시글 조회
    @GetMapping("/{board_id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> getBoard(@PathVariable Long board_id) {
        BoardResponseDto data = boardService.findBoard(board_id);
        ApiResponse<BoardResponseDto> response = new ApiResponse<>(HttpStatus.OK, "Board get successfully",data);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 게시물 수정
    @PatchMapping("/{board_id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(@PathVariable int board_id,
                                                          @RequestBody UpdateBoardDto updateBoardDto) {
        BoardResponseDto updatedBoard = boardService.updateBoard(board_id, updateBoardDto);
        ApiResponse<BoardResponseDto> response = new ApiResponse<>(HttpStatus.OK, "Board updated successfully",updatedBoard);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 게시글 삭제
    @DeleteMapping("/{board_id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoardById(@PathVariable int board_id) {
        boardService.deleteBoard(board_id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK, "Board Deleted successfully", null);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 댓글 작성
    @PostMapping("/{board_id}/comment")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@PathVariable() int board_id,
                                                          @RequestBody CreateCommentDto createCommentDto) {
        CommentResponseDto comment = boardService.createComment(board_id, createCommentDto);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, "Comment created successfully", null);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 대댓글 작성
    @PostMapping("/{board_id}/comment/{comment_id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createChildComment(@PathVariable() int board_id,
                                                               @PathVariable() int comment_id,
                                                               @RequestBody CreateCommentDto CreateCommentDto){
        boardService.createChildComment(board_id, comment_id, CreateCommentDto);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, "Child Comment created successfully", null);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 전체 댓글 조회
    @GetMapping("/{board_id}/comment")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getBoardList(@PathVariable() int board_id) {
        List<CommentResponseDto> data = boardService.findAllComments(board_id);
        ApiResponse<List<CommentResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "All comments get successfully",data);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 특정 댓글 조회
    @GetMapping("/{board_id}/comment/{comment_id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> getBoard(
            @PathVariable() int board_id, @PathVariable() int comment_id
    ) {
        CommentResponseDto data = boardService.findComment(board_id, comment_id);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.OK, "Comment get successfully",data);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 댓글 & 대댓글 수정
    @PatchMapping("/{board_id}/comment/{comment_id}")
    public ResponseEntity<ApiResponse<Void>> updateComment(@PathVariable() int board_id,
                                                          @PathVariable() int comment_id,
                                                          @RequestBody UpdateCommentDto updateCommentDto) {
        boardService.updateComment(board_id, comment_id, updateCommentDto);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED, "Comment updated successfully", null);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 댓글 & 대댓글 삭제
    @DeleteMapping("/{board_id}/comment/{comment_id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable() int board_id,
                                                          @PathVariable() int comment_id){
        boardService.deleteComment(board_id, comment_id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK, "Comment Deleted successfully", null);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 게시글 좋아요 등록 및 삭제
    @PostMapping("/{board_id}/like")
    public ResponseEntity<ApiResponse<BoardLikeResponseDto>> createBoardLike(@RequestParam() int user_id, @PathVariable() int board_id) {
        boardService.manageBoardLike(user_id,board_id);
        ApiResponse<BoardLikeResponseDto> response =  new ApiResponse<>(HttpStatus.CREATED, "Board Like Created successfully", null);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 댓글 좋아요 등록 및 삭제
    @PostMapping("/{board_id}/comment/{comment_id}/like")
    public ResponseEntity<ApiResponse<Void>> createCommentLike(
            @RequestParam() int user_id,
            @PathVariable() int board_id, @PathVariable() int comment_id){
        boardService.manageCommentLike(board_id, user_id,comment_id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED, "Comment Like Created successfully", null);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
