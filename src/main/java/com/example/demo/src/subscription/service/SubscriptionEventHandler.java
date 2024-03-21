package com.example.demo.src.subscription.service;

import com.example.demo.src.subscription.model.FirstPaymentSuccessEvent;
import com.example.demo.src.subscription.model.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubscriptionEventHandler {
    private final SubscriptionService subscriptionService;

    @EventListener
    public void onFirstPaymentSuccess(FirstPaymentSuccessEvent event) {
        subscriptionService.register(event.getPaymentResult());
    }

    @EventListener
    public void onUserDeleted(UserDeletedEvent event) {
        Long usrId = event.getUser().getId();

        if (subscriptionService.checkSubscriptionByUserId(usrId))
            subscriptionService.cancel(usrId);
    }

}
