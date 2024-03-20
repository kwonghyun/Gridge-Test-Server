package com.example.demo.src.subscription.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.User;
import lombok.*;
import org.hibernate.mapping.ToOne;

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
    @Column(updatable = false)
    private LocalDate previousPaymentDate;
    // 정기 결제일
    private Integer monthlyPaymentDay;
    @Column(nullable = false, updatable = false)
    private SubscriptionState subscriptionState;

    @OneToOne
    @JoinColumn(nullable = false, updatable = false)
    private PaymentHistory paymentHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @Builder.Default
    private boolean isLatest = true;

    public enum SubscriptionState {
        SUBSCRIBED, CANCELLED;
    }

    public void updateIsLatest() {
        this.isLatest = false;
    }

}
