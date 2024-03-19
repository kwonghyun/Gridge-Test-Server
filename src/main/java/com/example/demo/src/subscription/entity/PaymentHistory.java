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

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;

    @Column(nullable = false, updatable = false, unique = true)
    private String merchantUid;

    @Column(nullable = false, updatable = false, unique = true)
    private String customerUid;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "paymentHistory")
    private PaymentResult paymentResult;

    // 빌링키 발급(Type.BILLING_KEY) : PaymentState.(STAGED -> ISSUED_BILLING_KEY)
    // 정기 결제(Type.SCHEDULED_PAYMENT) : PaymentState.(STAGED -> SCHEDULED -> PAID) SCHEDULED, PAID 반복
    // 취소(Type.CANCELLATION) : PaymentState.(STAGED -> SCHEDULED_PAYMENT_CANCELLED -> REFUND -> BILLING_KEY_DELETED)

    public enum PaymentState {
        STAGED, ISSUED_BILLING_KEY, SCHEDULED, PAID, FAILED, SCHEDULED_PAYMENT_CANCELLED, REFUND, BILLING_KEY_DELETED;
    }

    public enum Type {
        SCHEDULED_PAYMENT, BILLING_KEY, CANCELLATION;
    }

}
