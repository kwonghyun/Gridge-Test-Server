package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.Feed;

public class PostFeedRes {
    private Long feedId;

    public PostFeedRes(Feed feed) {
        this.feedId = feed.getId();
    }
}
