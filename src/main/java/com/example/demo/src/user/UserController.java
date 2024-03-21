package com.example.demo.src.user;


import com.example.demo.common.Constant;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.user.model.PostConsentTermsReq;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 로그인 API
     * [POST] /app/users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("auth/login")
    public BaseResponse<PostLoginRes> login(@RequestBody @Valid PostLoginReq postLoginReq){
        PostLoginRes postLoginRes = userService.login(postLoginReq);
        return new BaseResponse<>(postLoginRes);
    }

    @ResponseBody
    @PostMapping("auth/dormant-login")
    public BaseResponse<PostLoginRes> login(@RequestBody @Valid PostConsentTermsReq req){
        PostLoginRes postLoginRes = userService.consentTermsLogin(req);
        return new BaseResponse<>(postLoginRes);
    }

    /**
     * 회원가입 API
     * [POST] /app/users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("auth/sign-up")
    public BaseResponse<String> createBasicUser(
            @RequestBody @Valid PostUserReq postUserReq
    ) {
        userService.createUser(postUserReq);
        return new BaseResponse<>(Constant.CREATED);
    }
    /**
     * 유저정보삭제 API
     * [DELETE] /app/users/:userId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PutMapping("/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable("userId") Long userId){
        Long jwtUserId = jwtService.getUserId();

        userService.deleteUser(userId);
        return new BaseResponse<>(Constant.DELETED);
    }

}
