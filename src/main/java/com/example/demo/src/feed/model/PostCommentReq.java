package com.example.demo.src.feed.model;

import com.example.demo.common.Constant;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class PostCommentReq {
    @NotBlank(message = Constant.COMMENT_CONTENT_LENGTH)
    @Length(min = 1, max = 200, message = Constant.COMMENT_CONTENT_LENGTH)
    private String content;
}
