package com.example.java_board.domain.board.service;

import com.example.java_board.domain.board.dto.request.CreateBoardDto;
import com.example.java_board.domain.board.dto.request.CreateCommentDto;
import com.example.java_board.domain.board.dto.request.UpdateBoardDto;
import com.example.java_board.domain.board.dto.request.UpdateCommentDto;
import com.example.java_board.domain.board.dto.response.BoardResponseDto;
import com.example.java_board.domain.board.dto.response.CommentResponseDto;
import com.example.java_board.domain.board.entity.Board;
import com.example.java_board.domain.board.entity.BoardLike;
import com.example.java_board.domain.board.entity.Comment;
import com.example.java_board.domain.board.entity.CommentLike;
import com.example.java_board.domain.board.repository.BoardLikeRepository;
import com.example.java_board.domain.board.repository.BoardRepository;
import com.example.java_board.domain.board.repository.CommentLikeRepository;
import com.example.java_board.domain.board.repository.CommentRepository;
import com.example.java_board.domain.user.entity.User;
import com.example.java_board.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository, CommentRepository commentRepository,
                        BoardLikeRepository boardLikeRepository, CommentLikeRepository commentLikeRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.boardLikeRepository = boardLikeRepository;
        this.commentLikeRepository = commentLikeRepository;
    }

    // 게시물 작성
    @Transactional
    public BoardResponseDto createBoard(CreateBoardDto createBoardDto) {
        User user = userRepository.findById(createBoardDto.userId()).orElseThrow(() -> new RuntimeException("user not found"));
        Board board = createBoardDto.toEntity(user);
        Board savedBoard = boardRepository.save(board);
        return new BoardResponseDto(savedBoard);
    }

    // 전체 게시물 조회
    public List<BoardResponseDto> findAllBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            boardResponseDtoList.add(boardResponseDto);
        }
        return boardResponseDtoList;
    }

    // 특정 게시물 조회
    public BoardResponseDto findBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        return new BoardResponseDto(board);
    }

    //게시물 수정
    @Transactional
    public BoardResponseDto updateBoard(long boardId, UpdateBoardDto updateBoardDto) {
        Board existingBoard = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        existingBoard.update(updateBoardDto);
        Board updatedBoard = boardRepository.save(existingBoard);
        return new BoardResponseDto(updatedBoard);
    }

    // 게시물 삭제
    @Transactional
    public void deleteBoard(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new RuntimeException("board not found"));
        boardRepository.deleteById(boardId);
    }

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(long boardId, CreateCommentDto createCommentDto) {
        User user = userRepository.findById(createCommentDto.user_id()).orElseThrow(() -> new RuntimeException("user not found"));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));

        Comment comment = createCommentDto.toEntity(user, board);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    // 전체 댓글 조회
    @Transactional()
    public List<CommentResponseDto> findAllComments(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        List<Comment> commentList = board.getComments();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }
        return commentResponseDtoList;
    }

    @Transactional
    public CommentResponseDto findComment(long boardId, long commentId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("comment not found"));
        return new CommentResponseDto(comment);
    }

    // 댓글/대댓글 수정
    @Transactional
    public CommentResponseDto updateComment(long boardId, long commentId, UpdateCommentDto updateCommentDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("comment not found"));
        existingComment.update(updateCommentDto);
        Comment updatedComment = commentRepository.save(existingComment);
        return new CommentResponseDto(updatedComment);
    }

    // 대댓글 작성
    @Transactional
    public CommentResponseDto createChildComment(long boardId, long parenCommentId, CreateCommentDto createCommentDto) {
        User user = userRepository.findById(createCommentDto.user_id()).orElseThrow(() -> new RuntimeException("user not found"));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        Comment parentComment = commentRepository.findByIdAndBoardId(parenCommentId, boardId).orElseThrow(()
                -> new RuntimeException("parent comment not found"));
        Comment childComment = new Comment(user, board, parentComment, createCommentDto);
        Comment savedComment = commentRepository.save(childComment);
        return new CommentResponseDto(savedComment);
    }

  // 댓글/대댓글 삭제
    @Transactional
    public void deleteComment(long boardId, long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("comment not found"));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        // 대댓글이 있는지 검사
        int cntChildComment = commentRepository.countChildComments(comment.getId());
        if (cntChildComment > 0) {
            // 대댓글이 있는 경우 - 실제 데이터를 삭제하지는 않고, is_deleted = false
            comment.updateDeleteStatus();
            commentRepository.save(comment);
        }else{
            // 대댓글이 없는 경우 - 데이터 삭제
            commentRepository.deleteById(commentId);
        }

    }
    // 게시글 좋아요 등록 및 삭제
    @Transactional
    public void manageBoardLike(long boardId, long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        Optional<BoardLike> existingBoardLike = boardLikeRepository.findByBoardIdAndUserId(boardId, userId);

        if (existingBoardLike.isPresent()) {
            long existBoardLikeId = existingBoardLike.get().getId();
            boardLikeRepository.deleteById(existBoardLikeId);
        }

        BoardLike boardLike = new BoardLike(user, board);
        boardLikeRepository.save(boardLike);
    }

    // 댓글 좋아요 등록 및 삭제
    @Transactional
    public void manageCommentLike(long boardId, long userId, long commentId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("board not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("comment not found"));

        Optional<CommentLike> existCommentLike = commentLikeRepository.findByCommentIdAndUserId(commentId,userId);

        if (existCommentLike.isPresent()) {
            long existCommentLikeId = existCommentLike.get().getId();
            commentRepository.deleteById(existCommentLikeId);
        };
        CommentLike commentLike = new CommentLike(user,comment);
        commentLikeRepository.save(commentLike);
    }
}
