package com.example.demo.src.subscription.repository;

import com.example.demo.src.subscription.entity.SubscriptionHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionHistory, Long> {
    @Query(
            "SELECT s FROM SubscriptionHistory s " +
                    "WHERE (s.user.id =:userId) " +
                    "AND (s.isLatest = true) "
    )
    Optional<SubscriptionHistory> findLatestByUserId(@Param("userId") Long userId);

    @Query(
            "SELECT s FROM SubscriptionHistory s " +
                    "WHERE (s.isLatest = true) " +
                    "AND (s.subscriptionState = com.example.demo.src.subscription.entity.SubscriptionHistory$SubscriptionState.SUBSCRIBED) " +
                    "AND (s.monthlyPaymentDay IN :monthlyPaymentDays) "
    )
    Slice<SubscriptionHistory> findAllByMonthlyPaymentDay(
            @Param("monthlyPaymentDays") List<Integer> monthlyPaymentDays,
            Pageable pageable
    );

    @Query(
            "SELECT COUNT (s.id) FROM SubscriptionHistory s " +
                    "WHERE (s.isLatest = true) " +
                    "AND (s.subscriptionState = com.example.demo.src.subscription.entity.SubscriptionHistory$SubscriptionState.SUBSCRIBED) " +
                    "AND (s.monthlyPaymentDay IN :monthlyPaymentDays) "
    )
    int countByMonthlyPaymentDay(
            @Param("monthlyPaymentDays") List<Integer> monthlyPaymentDays
    );
}
