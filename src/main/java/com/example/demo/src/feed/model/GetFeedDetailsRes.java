package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.Feed;
import lombok.Getter;

import java.util.List;
@Getter
public class GetFeedDetailsRes {
    private String name;
    private Long feedId;
    private int commentsCount;
    private int LikesCount;
    private boolean liked;
    private String content;
    private List<GetMediaRes> media;

    public GetFeedDetailsRes(Feed feed, int commentsCount, int likesCount, boolean liked, List<GetMediaRes> media) {
        this.name = feed.getUser().getName();
        this.feedId = feed.getId();
        this.commentsCount = commentsCount;
        this.content = feed.getContent();
        this.LikesCount = likesCount;
        this.liked = liked;
        this.media = media;
    }
}
