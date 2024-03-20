package com.example.demo.src.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostBillingKeyPaymentRes {
    private int code;
    private String message;
    private ResponseData response;

    @Getter
    public static class ResponseData {
        private int amount;
        @JsonProperty("apply_num")
        private String applyNum;
        @JsonProperty("bank_code")
        private String bankCode;
        @JsonProperty("bank_name")
        private String bankName;
        @JsonProperty("buyer_addr")
        private String buyerAddr;
        @JsonProperty("buyer_email")
        private String buyerEmail;
        @JsonProperty("buyer_name")
        private String buyerName;
        @JsonProperty("buyer_postcode")
        private String buyerPostcode;
        @JsonProperty("buyer_tel")
        private String buyerTel;
        @JsonProperty("cancel_amount")
        private int cancelAmount;
        @JsonProperty("cancel_history")
        private String[] cancelHistory;
        @JsonProperty("cancel_reason")
        private String cancelReason;
        @JsonProperty("cancel_receipt_urls")
        private String[] cancelReceiptUrls;
        @JsonProperty("cancelled_at")
        private int cancelledAt;
        @JsonProperty("card_code")
        private String cardCode;
        @JsonProperty("card_name")
        private String cardName;
        @JsonProperty("card_number")
        private String cardNumber;
        @JsonProperty("card_quota")
        private int cardQuota;
        @JsonProperty("card_type")
        private int cardType;
        @JsonProperty("cash_receipt_issued")
        private boolean cashReceiptIssued;
        private String channel;
        private String currency;
        @JsonProperty("custom_data")
        private String customData;
        @JsonProperty("customer_uid")
        private String customerUid;
        @JsonProperty("customer_uid_usage")
        private String customerUidUsage;
        @JsonProperty("emb_pg_provider")
        private String embPgProvider;
        private boolean escrow;
        @JsonProperty("fail_reason")
        private String failReason;
        @JsonProperty("failed_at")
        private int failedAt;
        @JsonProperty("imp_uid")
        private String impUid;
        @JsonProperty("merchant_uid")
        private String merchantUid;
        private String name;
        @JsonProperty("paid_at")
        private int paidAt;
        @JsonProperty("pay_method")
        private String payMethod;
        @JsonProperty("pg_id")
        private String pgId;
        @JsonProperty("pg_provider")
        private String pgProvider;
        @JsonProperty("pg_tid")
        private String pgTid;
        @JsonProperty("receipt_url")
        private String receiptUrl;
        @JsonProperty("started_at")
        private int startedAt;
        private String status;
        @JsonProperty("user_agent")
        private String userAgent;
        @JsonProperty("vbank_code")
        private String vbankCode;
        @JsonProperty("vbank_date")
        private int vbankDate;
        @JsonProperty("vbank_holder")
        private String vbankHolder;
        @JsonProperty("vbank_issued_at")
        private int vbankIssuedAt;
        @JsonProperty("vbank_name")
        private String vbankName;
        @JsonProperty("vbank_num")
        private String vbankNum;

    }
}
