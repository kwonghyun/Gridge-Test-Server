package com.example.demo.src.subscription.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Entity
public class SubscriptionHistory {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date subscrpitionStartDate;
    private Date subscrpitionEndDate;
    private Date lastPaymentDate;
    // 정기 결제일
    private Integer paymentDay;
    private SubscriptionState subscriptionState;

    public enum SubscriptionState {
        SUBSCRIBED, CANCELLED;
    }

}
