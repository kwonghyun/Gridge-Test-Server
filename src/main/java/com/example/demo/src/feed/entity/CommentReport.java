package com.example.demo.src.feed.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@DiscriminatorValue("COMMENT")
public class CommentReport extends Report{
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
}
