package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.MediaConnection;
import com.example.demo.src.feed.entity.MediaContent;

public class GetMediaRes {
    private Long mediaContentId;
    private int order;
    private String url;
    private MediaContent.Type type;

    public GetMediaRes(MediaConnection mediaConnection, String url) {
        this.mediaContentId = mediaConnection.getMediaContent().getId();
        this.order = mediaConnection.getDisplayOrder();
        this.url = url;
        this.type = mediaConnection.getMediaContent().getType();
    }
}
