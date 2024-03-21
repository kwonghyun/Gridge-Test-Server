package com.example.demo.common;

import java.time.format.DateTimeFormatter;

public class Constant {
    public enum SocialLoginType{
        GOOGLE,
        KAKAO,
        NAVER
    }
    // 공통

    public final static String REQUIRED = "필수 값입니다.";
    public final static String PAGE_VALID = "page 파라미터는 0 이상의 정수를 입력하세요.";
    public final static String SIZE_VALID = "size 파라미터는 0 이상의 정수를 입력하세요.";
    public final static String ORDER_VALID = "order는 0 이상의 정수를 입력하세요.";
    public final static String DATE_VALID = "날짜는 yyyy-mm-dd 형식으로 입력하세요";
    public static final String DATE_TIME_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public final static String CREATED = "생성됐습니다.";
    public final static String EDITED = "수정됐습니다.";
    public final static String DELETED = "삭제됐습니다.";

    // 유저
    public static final String LOGIN_ID_REGEX = "^[a-z0-9_.-]{1,20}$";
    public final static String PASSWORD_REGEX = "^(?=.*[a-zA-Z0-9_.])[a-zA-Z0-9_.]{6,20}$";
    public final static String PHONE_NUMBER_REGEX = "^010-\\d{4}-\\d{4}$";

    public final static String NAME_VALID = "이름은 1~20자를 입력하세요.";
    public final static String NAME_NOTNULL = "이름을 입력하세요.";
    public final static String LOGIN_ID_VALID = "아이디는 1~20 글자를 (영문 소문자, 숫자, '_', '.')만 사용해 입력하세요.";
    public final static String LOGIN_ID_NOTNULL = "아이디를 입력하세요.";
    public final static String PASSWORD_VALID = "비밀번호는 6~20 글자를 (영문 대·소문자, 숫자, '_', '.')를 하나 이상 포함해 입력하세요.";
    public final static String PASSWORD_NOTNULL = "비밀번호를 입력하세요.";
    public final static String CONSENT_REQUIRED = "모든 약관에 동의가 필요합니다.";
    public final static String PHONE_NUMBER_VALID = "휴대폰 번호를 000-0000-0000 형식으로 입력하세요.";
    public final static String PHONE_NUMBER_NOT_NULL = "휴대폰 번호를 입력하세요.";
    public final static String BIRTHDAY_NOT_NULL = "생일 입력하세요.";
    public final static String BIRTHDAY_VALID = "생일을 yyyy-mm-dd 형식으로 입력하세요.";
    public final static String SOCIAL_LOGIN_TYPE_VALID = "소셜로그인 타입을 KAKAO, GOOGLE, NAVER 중에 입력하세요.";

    // 피드
    public final static String FEED_CONTENT_LENGTH = "피드의 내용은 2200자 이내로 입력하세요.";
    public final static String COMMENT_CONTENT_LENGTH = "댓글의 내용은 1~200자 이내로 입력하세요.";
    public final static String REPORT_REASON_VALID = "신고 사유는 (SPAM, HATRED, VIOLENCE, BULLYING, HARASSMENT) 중 하나를 입력하세요.";
    public final static String COMMENT_ID_VALID = "commentId를 올바로 입력하세요.";
    public final static String FEED_ID_VALID = "feedId를 올바로 입력하세요.";
    public final static String MEDIA_CONTENT_ID_VALID = "mediaContentId를 올바로 입력하세요.";

    // 구독
    public final static String SUBSCRIPTION_CANCELED = "구독 취소 완료";






}

