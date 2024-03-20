package com.example.demo.src.subscription.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostPaymentRes {
    private final String pg = "html5_inicis.INIBillTst";
    private final String payMethod = "card";
    private final String name = "구독";

    private Integer amount;
    private String customerUid;
    private String merchantId;
}
