package com.example.demo.src.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUserRes {
    private Long id;

    public PostUserRes(Long id) {
        this.id = id;
    }
}
