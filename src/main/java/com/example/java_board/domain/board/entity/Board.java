package com.example.java_board.domain.board.entity;

import com.example.java_board.domain.board.dto.request.CreateBoardDto;
import com.example.java_board.domain.board.dto.request.UpdateBoardDto;
import lombok.Getter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.java_board.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user_email;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String  content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<BoardLike> boardLikes = new ArrayList<>();

    public Board(){}
    public Board(User user, CreateBoardDto createBoardDto) {
        this.title = createBoardDto.title();
        this.content = createBoardDto.content();
        this.user_email = user.getEmail();
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(UpdateBoardDto updateBoardDto) {
        this.title = updateBoardDto.title();
        this.content = updateBoardDto.content();
    }

}
