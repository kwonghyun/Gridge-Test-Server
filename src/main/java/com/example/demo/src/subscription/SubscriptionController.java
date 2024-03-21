package com.example.demo.src.subscription;

import com.example.demo.common.Constant;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.subscription.model.GetCheckSubscriptionRes;
import com.example.demo.src.subscription.service.SubscriptionService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("app/users/me/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final JwtService jwtService;

    @PostMapping("cancel")
    @ResponseBody
    public BaseResponse cancelSubscription() {
        Long jwtUserId = jwtService.getUserId();
        subscriptionService.cancel(jwtUserId);
        return new BaseResponse(Constant.SUBSCRIPTION_CANCELED);
    }

    @GetMapping("state")
    @ResponseBody
    public BaseResponse<GetCheckSubscriptionRes> getSubscriptionState() {
        Long jwtUserId = jwtService.getUserId();
        GetCheckSubscriptionRes res = subscriptionService.checkSubscriptionRes(jwtUserId);

        return new BaseResponse(res);
    }
}
