package com.example.demo.src.feed.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class GetFeedDetailsRes {
    private String name;
    private Long feedId;
    private int commentsCount;
    private int LikesCount;
    private boolean liked;
    private String content;
    private List<GetMediaRes> media;

}
