package com.example.demo.src.feed.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
public abstract class Report extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Reason reason;

    @JoinColumn(nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Report(Reason reason, User user) {
        this.user = user;
        this.reason = reason;
    }

    public enum Reason {
        SPAM, HATRED, VIOLENCE, BULLYING, HARASSMENT;
    }

}
