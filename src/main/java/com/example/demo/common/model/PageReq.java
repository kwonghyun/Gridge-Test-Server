package com.example.demo.common.model;

import com.example.demo.common.Constant;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
public class PageReq {

    Integer page;
    Integer size;

    public PageReq(
            @NotNull(message = Constant.PAGE_VALID)
            @PositiveOrZero(message = Constant.PAGE_VALID)
            Integer page,
            @NotNull(message = Constant.SIZE_VALID)
            @Positive(message = Constant.SIZE_VALID)
            Integer size) {
        this.page = page;
        this.size = size;
    }
}
