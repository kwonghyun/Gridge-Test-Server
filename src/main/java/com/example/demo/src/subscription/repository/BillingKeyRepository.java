package com.example.demo.src.subscription.repository;

import com.example.demo.src.subscription.entity.BillingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingKeyRepository extends JpaRepository<BillingKey, Long> {
}
