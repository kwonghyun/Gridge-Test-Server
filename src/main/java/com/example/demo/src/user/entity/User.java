package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.oauth.entity.OAuth;
import com.example.demo.src.subscription.entity.BillingKey;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String loginId;

    @Column(nullable = false)
    private String password;

    // 서비스 내에서 사용할 이름
    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 14)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDay;

    private LocalDateTime lastLoginAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserState userState = UserState.ACTIVE;

    @Column(nullable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserAuthority authority = UserAuthority.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OAuth> oAuth = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<BillingKey> billingKey = new ArrayList<>();


    public void updateName(String name) {
        this.name = name;
    }

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }
    public void updateUserState(UserState userState) {
        this.userState = userState;
    }

    public enum UserAuthority {
        USER, ADMIN;
    }

    public enum UserState {
        BANNED, DORMANT, ACTIVE
    }

}
