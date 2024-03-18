package com.example.demo.src.feed.entity;

import com.example.demo.src.user.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@DiscriminatorValue("COMMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommentReport extends Report{
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public CommentReport(Reason reason, User user, Comment comment) {
        super(reason, user);
        this.comment = comment;
    }
}
