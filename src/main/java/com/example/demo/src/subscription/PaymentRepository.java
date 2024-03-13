package com.example.demo.src.subscription;

import com.example.demo.src.subscription.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentHistory, Long> {
}
