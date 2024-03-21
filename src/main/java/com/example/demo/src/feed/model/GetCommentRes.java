package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class GetCommentRes {
    private String name;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;

    public GetCommentRes(Comment comment) {
        this.name = comment.getUser().getName();
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
