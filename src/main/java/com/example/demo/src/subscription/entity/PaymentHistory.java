package com.example.demo.src.subscription.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Entity
public class PaymentHistory extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer price;

    @Column(nullable = false, updatable = false, unique = true, length = 50)
    private String merchantUid;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(updatable = false, length = 50)
    private String impUidToCancel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private BillingKey billingKey;

    @OneToOne(mappedBy = "paymentHistory")
    private PaymentResult paymentResult;

    public enum Type {
        FIRST_PAYMENT, SCHEDULED_PAYMENT, BILLING_KEY, CANCELLATION;
    }

}
