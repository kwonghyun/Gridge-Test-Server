package com.example.demo.common;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.subscription.service.SubscriptionService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PageController {
    private final JwtService jwtService;
    private final SubscriptionService subscriptionService;

    @GetMapping("login")
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }


    @GetMapping("sign-up")
    public String signUp(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "loginType", required = false) String loginType,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws IOException {
        if (loginType != null) {
            try {
                Constant.SocialLoginType type = Constant.SocialLoginType.valueOf(loginType.toUpperCase());
                if (type == Constant.SocialLoginType.KAKAO && code != null) {
                    model.addAttribute("loginType", loginType.toLowerCase());
                    model.addAttribute("queryString", "?code=" + code);
                } else {
                    throw new BaseException(BaseResponseStatus.INVALID_OAUTH_TYPE);
                }
            } catch (Exception e) {
                response.sendRedirect("login");
            }
        } else {
            model.addAttribute("loginType", "auth");
            model.addAttribute("queryString", "");
        }

        return "sign-up";
    }
    @GetMapping("payment")
    public String paymentPage(
            @RequestParam(value = "accessToken", required = false) String accessToken,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws IOException {
        if (accessToken == null) {
            response.sendRedirect("/login");
        }
        Long userId = jwtService.getUserId(accessToken);

        if (subscriptionService.checkSubscriptionByUserId(userId)) {
            model.addAttribute("isSubscribed", true);
        } else {
            model.addAttribute("isSubscribed", false);
            model.addAttribute("storeId", "imp06817208");
            model.addAttribute("accessToken", accessToken);
        }
        String username = jwtService.getUsername(accessToken);
        model.addAttribute("name", username);
        return "payment";
    }
}
