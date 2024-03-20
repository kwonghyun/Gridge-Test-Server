package com.example.demo.src.oauth.entity;


import com.example.demo.common.Constant;
import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Entity
public class OAuth extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 소셜 로그인 플랫폼 아이디
    @Column(nullable = false)
    private String externalId;

    // 소셜 로그인 플랫폼 닉네임
    @Column(nullable = false)
    private String externalName;

    private Integer externalBirthDay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Constant.SocialLoginType socialLoginType;

    @JoinColumn(nullable = false, updatable = false, unique = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
}
