package com.example.demo.src.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetCustomerUidRes {
    private int code;
    private String message;
    @JsonProperty("response")
    private ResponseData responseData;
    @Getter
    public static class ResponseData {
        @JsonProperty("card_code")
        private String cardCode;

        @JsonProperty("card_name")
        private String cardName;

        @JsonProperty("card_number")
        private String cardNumber;

        @JsonProperty("card_type")
        private int cardType;

        @JsonProperty("customer_addr")
        private String customerAddress;

        @JsonProperty("customer_email")
        private String customerEmail;

        @JsonProperty("customer_id")
        private String customerId;

        @JsonProperty("customer_name")
        private String customerName;

        @JsonProperty("customer_postcode")
        private String customerPostcode;

        @JsonProperty("customer_tel")
        private String customerTel;

        @JsonProperty("customer_uid")
        private String customerUid;

        private long inserted;

        @JsonProperty("pg_id")
        private String pgId;

        @JsonProperty("pg_provider")
        private String pgProvider;

        private long updated;
    }

}
