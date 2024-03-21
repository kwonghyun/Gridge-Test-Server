package com.example.demo.src.report.entity;

import com.example.demo.src.feed.entity.Comment;
import com.example.demo.src.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@DiscriminatorValue("COMMENT")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport extends Report{
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Builder
    protected CommentReport(Reason reason, User user, Comment comment) {
        super(reason, user);
        this.comment = comment;
    }
}
