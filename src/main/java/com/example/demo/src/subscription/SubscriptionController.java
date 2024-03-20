package com.example.demo.src.subscription;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.subscription.service.SubscriptionService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("app/users/me/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final JwtService jwtService;

    @PostMapping("cancel")
    public BaseResponse cancelSubscription() {
        Long jwtUserId = jwtService.getUserId();
        subscriptionService.cancel(jwtUserId);
        return new BaseResponse("구독 취소");
    }

    @GetMapping("state")
    public BaseResponse getSubscriptionState() {
        Long jwtUserId = jwtService.getUserId();
        Boolean isSubscribed = subscriptionService.checkSubscription(jwtUserId);
        return new BaseResponse(isSubscribed);
    }
}
