package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.MediaContent;
import lombok.Getter;

@Getter
// Feed 생성시 이 객체의 정보를 가지고 이미지/동영상과의 연관관계 맺음
public class PostMediaContentRes {
    private Long mediaContentId;
    private String url;
    private MediaContent.Type type;

    public PostMediaContentRes(MediaContent mediaContent, String url) {
        this.mediaContentId = mediaContent.getId();
        this.url = url;
        this.type = mediaContent.getType();
    }
}
