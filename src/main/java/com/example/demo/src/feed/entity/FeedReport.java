package com.example.demo.src.feed.entity;

import lombok.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@DiscriminatorValue("FEED")
@Builder
public class FeedReport extends Report{
    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;
}
