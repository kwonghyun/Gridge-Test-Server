package com.example.demo.src.subscription.repository;

import com.example.demo.src.subscription.entity.PaymentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentResultRepository extends JpaRepository<PaymentResult, Long> {
    @Query(
            "SELECT r FROM  PaymentResult r JOIN FETCH r.paymentHistory " +
                    "WHERE r.impUid = :impUid "
    )
    Optional<PaymentResult> findByImpUid(@Param("impUid") String impUid);

}
