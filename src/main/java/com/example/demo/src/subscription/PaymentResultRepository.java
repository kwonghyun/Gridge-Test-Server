package com.example.demo.src.subscription;

import com.example.demo.src.subscription.entity.PaymentResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentResultRepository extends JpaRepository<PaymentResult, Long> {

}
