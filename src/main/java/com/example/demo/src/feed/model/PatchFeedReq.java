package com.example.demo.src.feed.model;

import com.example.demo.common.Constant;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class PatchFeedReq {
    @Length(max = 2200, message = Constant.FEED_CONTENT_LENGTH)
    private String content;
}
