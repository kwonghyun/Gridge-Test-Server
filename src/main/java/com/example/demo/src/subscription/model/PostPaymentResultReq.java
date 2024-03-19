package com.example.demo.src.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostPaymentResultReq {
    @JsonProperty("error_msg")
    private String errorMsg;
    @JsonProperty("imp_uid")
    private String impUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("pay_method")
    private String payMethod;
    @JsonProperty("pg_provider")
    private String pgProvider;
    @JsonProperty("pg_type")
    private String pgType;
    @JsonProperty("customer_uid")
    private String customerUid;
    @JsonProperty("paid_amount")
    private Integer paidAmount;
    private String currency;
    private String status;
    private Boolean success;
}
//{
//  "success": false,
//  "imp_uid": "imp_268424450329",
//  "merchant_uid": "1_sub_94d7a03f-0373-465b-bb0e-82fdf84423",
//  "pay_method": "card",
//  "pg_provider": "html5_inicis",
//  "pg_type": "payment",
//  "error_msg": "사용자가 결제를 취소하셨습니다"
//}


//{
//    "success": true,
//    "imp_uid": "imp_526747918699",
//    "pay_method": "card",
//    "merchant_uid": "1_sub_51587afa-8482-4287-80ab-c51622164a",
//    "name": "구독",
//    "paid_amount": 100,
//    "currency": "KRW",
//    "pg_provider": "html5_inicis",
//    "pg_type": "payment",
//    "pg_tid": "StdpayBillINIBillTst20240319211954848215",
//    "apply_num": "",
//    "buyer_name": "",
//    "buyer_email": "",
//    "buyer_tel": "",
//    "buyer_addr": "",
//    "buyer_postcode": "",
//    "custom_data": null,
//    "status": "paid",
//    "paid_at": 1710850795,
//    "receipt_url": "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=StdpayBillINIBillTst20240319211954848215&noMethod=1",
//    "card_name": "BC카드",
//    "bank_name": null,
//    "card_quota": 0,
//    "card_number": "944603*********7",
//    "customer_uid": "1_e3b8db1e-93e5-4aa3-a48c-49369b8f11df"
//}
