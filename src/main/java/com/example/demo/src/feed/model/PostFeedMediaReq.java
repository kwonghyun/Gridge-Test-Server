package com.example.demo.src.feed.model;

import com.example.demo.common.Constant;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
public class PostFeedMediaReq {
    @NotNull(message = Constant.ORDER_VALID)
    @PositiveOrZero(message = Constant.ORDER_VALID)
    private Integer order;
    @NotNull(message = Constant.MEDIA_CONTENT_ID_VALID)
    private Long mediaContentId;
}
