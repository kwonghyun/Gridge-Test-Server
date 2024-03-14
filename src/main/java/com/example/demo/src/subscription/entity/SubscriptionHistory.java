package com.example.demo.src.subscription.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Entity
public class SubscriptionHistory extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDate subscriptionStartDate;
    @Column(updatable = false)
    private LocalDate subscriptionEndDate;
    @Column(nullable = false, updatable = false)
    private LocalDate previousPaymentDate;
    // 정기 결제일
    private Integer paymentDay;
    private SubscriptionState subscriptionState;

    @OneToOne
    @JoinColumn(nullable = false, updatable = false)
    private PaymentHistory paymentHistory;

    public enum SubscriptionState {
        SUBSCRIBED, CANCELLED;
    }

}
