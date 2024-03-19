package com.example.demo.src.oauth.model;

import com.example.demo.common.Constant;
import com.example.demo.src.oauth.entity.OAuth;
import com.example.demo.src.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

// 카카오(서드파티)로 액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@Getter
public class KakaoUser {
    private long id;
    @JsonProperty("connected_at")
    private String connectedAt;
    private Properties properties;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class Properties {
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

    @Getter
    public static class KakaoAccount {
        @JsonProperty("profile_needs_agreement")
        private Boolean profileNeedsAgreement;
        private Profile profile;
        @JsonProperty("has_email")
        private Boolean hasEmail;
        @JsonProperty("email_needs_agreement")
        private Boolean emailNeedsAgreement;
        @JsonProperty("is_email_valid")
        private Boolean isEmailValid;
        @JsonProperty("is_email_verified")
        private Boolean isEmailVerified;
        private String email;
        private String birthday;
        @JsonProperty("has_age_range")
        private Boolean hasAgeRange;
        @JsonProperty("age_range_needs_agreement")
        private Boolean ageRangeNeedsAgreement;
        @JsonProperty("has_birthday")
        private Boolean hasBirthday;
        @JsonProperty("birthday_needs_agreement")
        private Boolean birthdayNeedsAgreement;
        @JsonProperty("has_gender")
        private Boolean hasGender;
        @JsonProperty("gender_needs_agreement")
        private Boolean genderNeedsAgreement;

        @Getter
        public static class Profile {
            private String nickname;
            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }

    public OAuth toEntity(User user) {
        return OAuth.builder()
                .externalId(kakaoAccount.email)
                .externalName(properties.nickname)
                .externalBirthDay(Integer.parseInt(kakaoAccount.birthday))
                .socialLoginType(Constant.SocialLoginType.KAKAO)
                .user(user)
                .build();
    }
}