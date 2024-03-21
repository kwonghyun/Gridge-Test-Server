package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.Feed;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
public class GetFeedRes {
    private String name;
    private int likesCount;
    private boolean liked;
    private String content;
    private int commentsCount;
    private LocalDateTime createdAt;

    private List<GetCommentRes> comments;
    private List<GetMediaRes> mediaContents;

    public GetFeedRes(Feed feed, boolean liked, int likesCount, int commentsCount, List<GetCommentRes> comments, List<GetMediaRes> media) {
        this.name = feed.getUser().getName();
        this.createdAt = feed.getCreatedAt();
        this.likesCount = likesCount;
        this.liked = liked;
        this.content = feed.getContent();
        this.comments = comments;
        this.commentsCount = commentsCount;
        this.mediaContents = media;
    }
}
