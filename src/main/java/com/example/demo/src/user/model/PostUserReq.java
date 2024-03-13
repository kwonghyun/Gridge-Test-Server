package com.example.demo.src.user.model;

import com.example.demo.src.user.entity.Terms;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {
    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String birthday;
    private String dataTermsConsent;
    private String usageTermsConsent;
    private String locationTermsConsent;

    public User toUserEntity() {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .birthDay(LocalDate.parse(birthday))
                .build();
    }

    public Terms toTermsEntity(User user) {
        return Terms.builder()
                .consentDate(LocalDate.now())
                .dataTermsConsent(Boolean.parseBoolean(dataTermsConsent))
                .usageTermsConsent(Boolean.parseBoolean(usageTermsConsent))
                .locationTermsConsent(Boolean.parseBoolean(locationTermsConsent))
                .consentState(Terms.TermsConsentState.VALID)
                .user(user)
                .build();
    }
}
