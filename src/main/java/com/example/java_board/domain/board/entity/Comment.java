package com.example.java_board.domain.board.entity;

import com.example.java_board.domain.board.dto.request.CreateCommentDto;
import com.example.java_board.domain.board.dto.request.UpdateCommentDto;
import com.example.java_board.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String  content;

    private Boolean is_deleted;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    // 부모 댓글이 '삭제된 댓글입니다' 라고 표시되더라도, 대댓글은 삭제 안되는 경우가 많으므로(cascade 설정x)
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childCommentList;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLike> likeList;

    // 기본 생성자
    public Comment(){}

    public Comment(User user, Board board, CreateCommentDto createCommentDto){
        this.content = createCommentDto.content();
        this.board = board;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.is_deleted = Boolean.FALSE;
    }

    public Comment(User user, Board board, Comment ParentComment, CreateCommentDto createCommentDto) {
        this.content = createCommentDto.content();
        this.board = board;
        this.user = user;
        this.parentComment = ParentComment;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.is_deleted = Boolean.FALSE;
    }

   public void update(UpdateCommentDto updateCommentDto){
        this.content = updateCommentDto.content();
   }

    public  void updateDeleteStatus() {
        this.content = "삭제된 댓글입니다.";
        this.is_deleted = Boolean.TRUE;
    }
}
