package com.example.demo.src.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PostBillingKeyPaymentReq {
    @JsonProperty("customer_uid")
    private String customerUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
    private int amount;
    private final String name = "구독";
}
