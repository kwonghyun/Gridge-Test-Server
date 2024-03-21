package com.example.demo.src.user.model;

import com.example.demo.common.Constant;
import lombok.Getter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class PostConsentTermsReq {

    @Pattern(regexp = Constant.LOGIN_ID_REGEX, message = Constant.LOGIN_ID_VALID)
    @NotBlank(message = Constant.LOGIN_ID_NOTNULL)
    private String loginId;

    @Pattern(regexp = Constant.PASSWORD_REGEX, message = Constant.PASSWORD_VALID)
    @NotBlank(message = Constant.PASSWORD_NOTNULL)
    private String password;

    @NotNull(message = Constant.CONSENT_REQUIRED)
    @AssertTrue(message = Constant.CONSENT_REQUIRED)
    private Boolean dataTermsConsent;

    @NotNull(message = Constant.CONSENT_REQUIRED)
    @AssertTrue(message = Constant.CONSENT_REQUIRED)
    private Boolean usageTermsConsent;

    @NotNull(message = Constant.CONSENT_REQUIRED)
    @AssertTrue(message = Constant.CONSENT_REQUIRED)
    private Boolean locationTermsConsent;
}
