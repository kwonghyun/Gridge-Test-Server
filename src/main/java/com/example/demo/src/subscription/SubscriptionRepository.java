package com.example.demo.src.subscription;

import com.example.demo.src.subscription.entity.SubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionHistory, Long> {
    @Query(
            "SELECT s FROM SubscriptionHistory s " +
                    "WHERE s.user.id =:userId " +
                    "ORDER BY s.createdAt DESC "
    )
    Optional<SubscriptionHistory> findLatestByUserId(@Param("userId") Long userId);
}
