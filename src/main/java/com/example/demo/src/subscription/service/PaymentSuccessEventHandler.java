package com.example.demo.src.subscription.service;

import com.example.demo.src.subscription.model.FirstPaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentSuccessEventHandler {
    private final SubscriptionService subscriptionService;

    @EventListener
    public void onFirstPaymentSuccess(FirstPaymentSuccessEvent event) {
        subscriptionService.register(event.getPaymentResult());
    }

}
