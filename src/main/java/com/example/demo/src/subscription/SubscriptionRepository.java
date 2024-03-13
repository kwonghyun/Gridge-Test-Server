package com.example.demo.src.subscription;

import com.example.demo.src.subscription.entity.SubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionHistory, Long> {
}
