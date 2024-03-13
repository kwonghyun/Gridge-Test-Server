package com.example.demo.src.feed.entity;

import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@DiscriminatorValue("FEED")
@SuperBuilder
public class FeedReport extends Report{
    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;
}
