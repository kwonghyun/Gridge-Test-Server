package com.example.demo.src.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PortOneWebhookReq {
    @JsonProperty("status")
    private String status;
    @JsonProperty("imp_uid")
    private String impUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("cancellation_id")
    private String cancellationId;
}