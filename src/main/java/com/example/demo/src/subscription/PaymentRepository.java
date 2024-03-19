package com.example.demo.src.subscription;

import com.example.demo.src.subscription.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentHistory, Long> {
    @Query(
            "SELECT p FROM PaymentHistory p " +
                    "WHERE (p.merchantUid = :merchantId) " +
                    "AND (p.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "ORDER BY p.createdAt DESC "
    )
    Optional<PaymentHistory> findByMerchantUid(@Param("merchantId") String merchantUid);
}
