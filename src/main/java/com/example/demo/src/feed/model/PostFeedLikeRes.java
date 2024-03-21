package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.FeedLike;
import lombok.Getter;

@Getter
public class PostFeedLikeRes {
    private Long feedId;
    private boolean isLiked;

    public PostFeedLikeRes(FeedLike feedLike) {
        this.feedId = feedLike.getFeed().getId();
        this.isLiked = feedLike.isLiked();
    }
}
