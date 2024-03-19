package com.example.demo.src.subscription;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.subscription.model.PortOneWebhookReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

}
