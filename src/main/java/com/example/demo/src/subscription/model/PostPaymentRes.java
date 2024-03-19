package com.example.demo.src.subscription.model;

import com.example.demo.src.subscription.entity.PaymentHistory;
import lombok.Getter;

@Getter
public class PostPaymentRes {
    private final String pg = "html5_inicis.INIBillTst";
    private final String payMethod = "card";
    private final String name = "구독";

    private Integer amount;
    private String customerUid;
    private String merchantId;

    public PostPaymentRes(PaymentHistory paymentHistory) {
        this.amount = paymentHistory.getPrice();
        this.customerUid = paymentHistory.getCustomerUid();
        this.merchantId = paymentHistory.getMerchantUid();
    }
}
