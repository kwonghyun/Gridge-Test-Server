package com.example.demo.src.subscription.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResult extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String impUid;

    @Column(updatable = false, length = 50)
    private String cancellationId;

    @Column(updatable = false)
    private String message;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PayState payState;

    @OneToOne
    @JoinColumn(nullable = false, updatable = false)
    private PaymentHistory paymentHistory;

    public enum PayState {
        PAID, FAILED, CANCELLED;
    }

}
