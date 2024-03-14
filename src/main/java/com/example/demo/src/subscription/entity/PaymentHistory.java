package com.example.demo.src.subscription.entity;

import com.example.demo.common.entity.BaseEntity;
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

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum PaymentState {
        STAGED, ON_PROGRESS, COMPLETED, FAILED, CANCELLED;
    }

    public enum Type {
        RENEWAL, NEW, CANCELLATION;
    }

}
