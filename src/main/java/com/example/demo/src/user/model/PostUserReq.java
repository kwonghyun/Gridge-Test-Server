package com.example.demo.src.user.model;

import com.example.demo.common.Constant;
import com.example.demo.common.validation.DatePattern;
import com.example.demo.src.user.entity.Terms;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {

    @Pattern(regexp = Constant.LOGIN_ID_REGEX, message = Constant.LOGIN_ID_VALID)
    @NotBlank(message = Constant.LOGIN_ID_NOTNULL)
    private String loginId;

    @Pattern(regexp = Constant.PASSWORD_REGEX, message = Constant.PASSWORD_VALID)
    @NotBlank(message = Constant.PASSWORD_NOTNULL)
    private String password;

    @Length(min = 1, max = 20, message = Constant.NAME_VALID)
    @NotBlank(message = Constant.NAME_NOTNULL)
    private String name;

    @NotBlank(message = Constant.PHONE_NUMBER_NOT_NULL)
    @Pattern(regexp = Constant.PHONE_NUMBER_REGEX, message = Constant.PHONE_NUMBER_VALID)
    private String phoneNumber;

    @NotNull(message = Constant.BIRTHDAY_NOT_NULL)
    @DatePattern(message = Constant.BIRTHDAY_VALID)
    private String birthday;

    @NotNull(message = Constant.CONSENT_REQUIRED)
    @AssertTrue(message = Constant.CONSENT_REQUIRED)
    private Boolean dataTermsConsent;

    @NotNull(message = Constant.CONSENT_REQUIRED)
    @AssertTrue(message = Constant.CONSENT_REQUIRED)
    private Boolean usageTermsConsent;

    @NotNull(message = Constant.CONSENT_REQUIRED)
    @AssertTrue(message = Constant.CONSENT_REQUIRED)
    private Boolean locationTermsConsent;

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
                .dataTermsConsent(dataTermsConsent)
                .usageTermsConsent(usageTermsConsent)
                .locationTermsConsent(locationTermsConsent)
                .consentState(Terms.TermsConsentState.VALID)
                .user(user)
                .build();
    }
}
