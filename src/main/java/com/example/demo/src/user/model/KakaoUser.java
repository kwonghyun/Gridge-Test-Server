package com.example.demo.src.user.model;

import com.example.demo.common.Constant;
import com.example.demo.src.user.entity.OAuth;
import com.example.demo.src.user.entity.User;
import lombok.Getter;

// 카카오(서드파티)로 액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@Getter
public class KakaoUser {
    private long id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Getter
    public static class KakaoAccount {
        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
        private String birthday;
        private Boolean has_age_range;
        private Boolean age_range_needs_agreement;
        private Boolean has_birthday;
        private Boolean birthday_needs_agreement;
        private Boolean has_gender;
        private Boolean gender_needs_agreement;

        @Getter
        public static class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
        }
    }

    public OAuth toEntity(User user) {
        return OAuth.builder()
                .externalId(kakao_account.email)
                .externalName(properties.nickname)
                .externalBirthDay(Integer.parseInt(kakao_account.birthday))
                .socialLoginType(Constant.SocialLoginType.KAKAO)
                .user(user)
                .build();
    }
}
