package com.example.demo.src.subscription;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.subscription.entity.SubscriptionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public Boolean checkSubscription(Long userId) {
        Optional<SubscriptionHistory> optional = subscriptionRepository.findLatestByUserId(userId);
        if (
                optional.isPresent()
                && optional.get().getSubscriptionState() == SubscriptionHistory.SubscriptionState.SUBSCRIBED
        ) {
            return true;
        } else {
            return false;
        }
    }

    public SubscriptionHistory getLatestSubscription(Long userId) {
        SubscriptionHistory subscription = subscriptionRepository.findLatestByUserId(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_SUBSCRIPTION));
        return subscription;
    }
}
