package com.example.demo.src.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PostCancellationRes {
    private int code;
    private String message;
    private Response response;

    @Getter
    public static class Response {
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
        private CancelHistory[] cancelHistory;
        @JsonProperty("cancel_reason")
        private String cancelReason;
        @JsonProperty("cancel_receipt_urls")
        private String[] cancelReceiptUrls;
        @JsonProperty("cancelled_at")
        private long cancelledAt;
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
        private Object customData;
        @JsonProperty("customer_uid")
        private String customerUid;
        @JsonProperty("customer_uid_usage")
        private String customerUidUsage;
        @JsonProperty("emb_pg_provider")
        private Object embPgProvider;
        private boolean escrow;
        @JsonProperty("fail_reason")
        private Object failReason;
        @JsonProperty("failed_at")
        private long failedAt;
        @JsonProperty("imp_uid")
        private String impUid;
        @JsonProperty("merchant_uid")
        private String merchantUid;
        private String name;
        @JsonProperty("paid_at")
        private long paidAt;
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
        private long startedAt;
        private String status;
        @JsonProperty("user_agent")
        private String userAgent;
        @JsonProperty("vbank_code")
        private Object vbankCode;
        @JsonProperty("vbank_date")
        private long vbankDate;
        @JsonProperty("vbank_holder")
        private Object vbankHolder;
        @JsonProperty("vbank_issued_at")
        private long vbankIssuedAt;
        @JsonProperty("vbank_name")
        private Object vbankName;
        @JsonProperty("vbank_num")
        private Object vbankNum;
    }

    @Getter
    public static class CancelHistory {
        @JsonProperty("pg_tid")
        private String pgTid;
        private int amount;
        @JsonProperty("cancelled_at")
        private long cancelledAt;
        private String reason;
        @JsonProperty("receipt_url")
        private String receiptUrl;
        @JsonProperty("cancellation_id")
        private String cancellationId;
    }
}