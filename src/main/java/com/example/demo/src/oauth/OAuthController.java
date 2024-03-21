package com.example.demo.src.oauth;

import com.example.demo.common.Constant;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.validation.EnumValueCheck;
import com.example.demo.src.user.model.GetSocialOAuthRes;
import com.example.demo.src.user.model.PostUserReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    private final OAuthService oAuthService;

    /**
     * 회원가입 API
     * [POST] /app/users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("app/users/{socialLoginType}/sign-up")
    public BaseResponse<String> createOAuthUser(
            @PathVariable(name="socialLoginType")
            @EnumValueCheck(enumClass = Constant.SocialLoginType.class, message = Constant.SOCIAL_LOGIN_TYPE_VALID)
            String socialLoginPath,
            @RequestParam("code") @NotNull(message = Constant.REQUIRED) String code,
            @RequestBody @Valid PostUserReq postUserReq
    ) throws JsonProcessingException {
        Constant.SocialLoginType socialLoginType= Constant.SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        oAuthService.createOAuthUser(postUserReq, socialLoginType, code);
        return new BaseResponse<>(Constant.CREATED);
    }

    /**
     * 유저 소셜 가입, 로그인 인증으로 리다이렉트 해주는 url
     * [GET] auth/:socialLoginType/login
     * @return void
     */
    @ResponseBody
    @GetMapping("app/users/{socialLoginType}/login")
    public void socialLoginRedirect(
            @PathVariable(name="socialLoginType")
            @EnumValueCheck(enumClass = Constant.SocialLoginType.class, message = Constant.SOCIAL_LOGIN_TYPE_VALID)
            String socialLoginPath) throws IOException {
        Constant.SocialLoginType socialLoginType= Constant.SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        oAuthService.accessRequest(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginPath (GOOGLE, NAVER, KAKAO)
     * @param code API Server 로부터 넘어오는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 java 객체 (access_token, jwt_token, user_num 등)
     */
    @ResponseBody
    @GetMapping(value = "/login/{socialLoginType}")
    public BaseResponse<GetSocialOAuthRes> socialLoginCallback(
            @PathVariable(name = "socialLoginType")
            @EnumValueCheck(enumClass = Constant.SocialLoginType.class, message = Constant.SOCIAL_LOGIN_TYPE_VALID)
            String socialLoginPath,
            @RequestParam(name = "code")
            @NotNull(message = Constant.REQUIRED)
            String code) throws IOException, BaseException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        Constant.SocialLoginType socialLoginType = Constant.SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes = oAuthService.oAuthLoginOrRedirectToSignUp(socialLoginType, code);
        return new BaseResponse<>(getSocialOAuthRes);
    }
}
