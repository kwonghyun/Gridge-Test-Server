package com.example.demo.src.feed.model;

import lombok.Getter;

import java.util.List;
@Getter
public class PostFeedReq {
    private List<PostFeedMediaReq> media;
    private String content;
}
