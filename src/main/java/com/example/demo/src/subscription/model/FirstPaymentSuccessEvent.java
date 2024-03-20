package com.example.demo.src.subscription.model;

import com.example.demo.src.subscription.entity.PaymentResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FirstPaymentSuccessEvent {
    private PaymentResult paymentResult;
}
