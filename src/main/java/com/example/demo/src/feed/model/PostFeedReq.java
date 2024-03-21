package com.example.demo.src.feed.model;

import com.example.demo.common.Constant;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;
@Getter
public class PostFeedReq {
    private List<PostFeedMediaReq> media;

    @Length(max = 2200, message = Constant.FEED_CONTENT_LENGTH)
    private String content;
}
