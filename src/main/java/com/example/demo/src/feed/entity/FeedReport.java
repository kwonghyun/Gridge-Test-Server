package com.example.demo.src.feed.entity;

import com.example.demo.src.user.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FEED")
@Entity
public class FeedReport extends Report{
    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

    public FeedReport(Reason reason, User user, Feed feed) {
        super(reason, user);
        this.feed = feed;
    }
}

