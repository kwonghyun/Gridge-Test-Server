package com.example.demo.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */

    USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    TEST_EMPTY_COMMENT(false, HttpStatus.BAD_REQUEST.value(), "코멘트를 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"중복된 이메일입니다."),
    POST_TEST_EXISTS_MEMO(false,HttpStatus.BAD_REQUEST.value(),"중복된 메모입니다."),

    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),

    DUPLICATED_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    INVALID_MEMO(false,HttpStatus.NOT_FOUND.value(), "존재하지 않는 메모입니다."),
    FAILED_TO_LOGIN(false,HttpStatus.NOT_FOUND.value(),"없는 아이디거나 비밀번호가 틀렸습니다."),
    EMPTY_JWT(false, HttpStatus.UNAUTHORIZED.value(), "JWT를 입력해주세요."),
    EXPIRED_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효기간이 만료된 JWT입니다. 다시 로그인 해주세요."),
    INVALID_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    UNSUPPORTED_JWT(false, HttpStatus.UNAUTHORIZED.value(), "지원하지 않는 형식의 JWT입니다."),
    INVALID_SIGNATURE_JWT(false, HttpStatus.UNAUTHORIZED.value(), "서명이 잘못된 JWT입니다."),
    INVALID_USER_JWT(false,HttpStatus.FORBIDDEN.value(),"권한이 없는 유저의 접근입니다."),
    NOT_FIND_USER(false,HttpStatus.NOT_FOUND.value(),"일치하는 유저가 없습니다."),
    INVALID_OAUTH_TOKEN(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 소셜 토큰입니다."),
    NOT_FIND_OAUTH_USER(false,HttpStatus.NOT_FOUND.value(),"일치하는 소셜 유저 정보가 없습니다."),
    INVALID_OAUTH_TYPE(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 소셜 로그인 형식입니다."),


    NOT_FIND_FEED(false,HttpStatus.NOT_FOUND.value(),"일치하는 피드가 없습니다."),
    NOT_FIND_PAYMENT(false,HttpStatus.NOT_FOUND.value(),"일치하는 결제기록이 없습니다."),
    FAKE_PAYMENT_INFO(false,HttpStatus.BAD_REQUEST.value(),"위조된 결제가 발생했습니다."),
    ISSUE_BILLING_KEY_FAILED(false,HttpStatus.BAD_REQUEST.value(),"빌링키 발급에 실패했습니다."),
    NOT_FIND_BILLING_KEY(false,HttpStatus.BAD_REQUEST.value(),"빌링키가 없습니다. 발급해주세요."),
    NOT_AUTHORIZED_FEED(false,HttpStatus.UNAUTHORIZED.value(), "접근 권한이 없는 피드입니다."),

    NOT_FIND_COMMENT(false,HttpStatus.NOT_FOUND.value(),"일치하는 댓글이 없습니다."),
    NOT_FIND_SUBSCRIPTION(false,HttpStatus.NOT_FOUND.value(),"구독정보가 없습니다."),
    NOT_FIND_ALIVE_SUBSCRIPTION(false,HttpStatus.NOT_FOUND.value(),"구독중이 아닙니다."),
    NOT_FIND_FEED_LIKE(false,HttpStatus.NOT_FOUND.value(),"일치하는 좋아요가 없습니다."),

    NOT_AUTHORIZED_COMMENT(false,HttpStatus.UNAUTHORIZED.value(), "접근 권한이 없는 댓글입니다."),
    NOT_AUTHORIZED(false,HttpStatus.UNAUTHORIZED.value(), "로그인해야 이용할 수 있는 서비스 입니다."),

    NOT_MATCH_FEED_MEDIA_CONTENT(false,HttpStatus.BAD_REQUEST.value(), "요청한 피드의 사진 또는 동영상이 아닙니다."),
    UNSUPPORTED_MEDIA_TYPE(false,HttpStatus.BAD_REQUEST.value(), "지원하는 형식의 사진(\"jpg\", \"jpeg\", \"png\", \"gif\") 또는 동영상(\"mp4\", \"avi\", \"mov\", \"mkv\")이 아닙니다."),
    TOO_MANY_MEDIA_CONTENT(false,HttpStatus.BAD_REQUEST.value(), "사진 또는 동영상이 너무 많습니다. 파일은 10개까지 선택 가능합니다."),
    NOT_MATCH_FEED_COMMENT(false,HttpStatus.BAD_REQUEST.value(), "요청한 피드의 댓글이 아닙니다."),
    NOT_FIND_MEDIA_CONTENT(false,HttpStatus.NOT_FOUND.value(),"일치하는 사진 또는 동영상이 없습니다."),

    ALREADY_REPORTED_FEED(false,HttpStatus.BAD_REQUEST.value(),"이미 신고한 피드입니다."),
    ALREADY_REPORTED_COMMENT(false,HttpStatus.BAD_REQUEST.value(),"이미 신고한 댓글입니다."),


    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다."),
    JSON_PROCESSING_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "JSON을 파싱하는데 실패하였습니다."),

    UPLOAD_FAIL_MEDIA_CONTENT(false,HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 업로드에 실패했습니다."),
    GET_PORT_ONE_TOKEN_FAIL(false,HttpStatus.INTERNAL_SERVER_ERROR.value(), "포트원 토큰 불러오기에 실패했습니다."),

    FAILED_PAYMENT(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"결제에 실패했습니다."),
    FAILED_PAYMENT_CANCELLATION(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"결제 취소에 실패했습니다."),

    MODIFY_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저 삭제 실패"),
    MODIFY_FAIL_MEMO(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"메모 수정 실패"),

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
