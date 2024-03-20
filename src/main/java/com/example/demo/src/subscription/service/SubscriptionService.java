package com.example.demo.src.subscription.service;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.subscription.entity.PaymentHistory;
import com.example.demo.src.subscription.entity.PaymentResult;
import com.example.demo.src.subscription.entity.SubscriptionHistory;
import com.example.demo.src.subscription.repository.SubscriptionRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentService paymentService;

    // 구독 여부 확인
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

    // 유저의 최신 구독 기록 반환
    public SubscriptionHistory getLatestAliveSubscription(Long userId) {
        SubscriptionHistory subscription = subscriptionRepository.findLatestByUserId(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_SUBSCRIPTION));
        if (subscription.getSubscriptionState() != SubscriptionHistory.SubscriptionState.CANCELLED) {
            throw new BaseException(BaseResponseStatus.NOT_FIND_ALIVE_SUBSCRIPTION);
        }
        return subscription;
    }

    @Transactional
    // 결제 완료 후 신규 구독 등록
    public void register(PaymentResult paymentResult) {
        PaymentHistory paymentHistory = paymentResult.getPaymentHistory();
        User user = paymentHistory.getUser();

        subscriptionRepository.save(SubscriptionHistory.builder()
                .monthlyPaymentDay(paymentResult.getCreatedAt().getDayOfMonth())
                .subscriptionStartDate(paymentResult.getCreatedAt().toLocalDate())
                .subscriptionState(SubscriptionHistory.SubscriptionState.SUBSCRIBED)
                .paymentHistory(paymentHistory)
                .user(user)
                .build()
        );
    }

    // 정기 결제에 의한 갱신(재결제)
    @Transactional
    public void renew(SubscriptionHistory subscription) {
        Long userId = subscription.getUser().getId();
        PaymentResult paymentResult = paymentService.monthlyPay(userId);
        PaymentHistory paymentHistory = paymentResult.getPaymentHistory();
        User user = paymentHistory.getUser();
        subscription.updateIsLatest();
        LocalDateTime lastPaidAt = subscription.getCreatedAt();
        subscriptionRepository.save(
                SubscriptionHistory.builder()
                        .monthlyPaymentDay(subscription.getMonthlyPaymentDay())
                        .subscriptionStartDate(subscription.getSubscriptionStartDate())
                        .previousPaymentDate(lastPaidAt.toLocalDate())
                        .subscriptionState(SubscriptionHistory.SubscriptionState.SUBSCRIBED)
                        .paymentHistory(paymentHistory)
                        .user(user)
                        .build()
        );
    }

    // 구독 취소 및 마지막 결제 환불
    @Transactional
    public void cancel(Long userId) {
        SubscriptionHistory subscription = getLatestAliveSubscription(userId);
        subscription.updateIsLatest();
        String impUid = subscription.getPaymentHistory().getPaymentResult().getImpUid();

        PaymentResult paymentResult = paymentService.cancelPay(impUid);
        PaymentHistory paymentHistory = paymentResult.getPaymentHistory();
        User user = paymentHistory.getUser();

        LocalDateTime lastPaidAt = subscription.getCreatedAt();
        subscriptionRepository.save(
                SubscriptionHistory.builder()
                        .monthlyPaymentDay(subscription.getMonthlyPaymentDay())
                        .subscriptionStartDate(subscription.getSubscriptionStartDate())
                        .previousPaymentDate(lastPaidAt.toLocalDate())
                        .subscriptionEndDate(paymentResult.getCreatedAt().toLocalDate())
                        .subscriptionState(SubscriptionHistory.SubscriptionState.CANCELLED)
                        .paymentHistory(paymentHistory)
                        .user(user)
                        .build()
        );
    }

    // 매일 오후 2시에 정기 결제 실행
    @Scheduled(cron = "0 14 * * *")
    public void renewSubscriptionsEveryday() {
        LocalDate now = LocalDate.now();
        Integer day = now.getDayOfMonth();
        Integer biggestDayOfMonths = 31;
        Integer lastDayOfMonth = now.lengthOfMonth();
        List<Integer> days = new ArrayList<>();

        // 이번달이 31일 보다 작고 오늘이 마지막 날이면
        // 그 뒷 날짜 결제 예정인 구독도 재결제
        if (
                now.getDayOfMonth() == lastDayOfMonth
                && !day.equals(biggestDayOfMonths)
        ) {
            for (int i = day; i <= biggestDayOfMonths; i++) days.add(i);
        } else {
            days.add(day);
        }

        int batchSize = 100;
        int countToPayment = subscriptionRepository.countByMonthlyPaymentDay(days);
        int totalPages = (int) Math.ceil(((double) countToPayment) / batchSize);

        for (int i = 0; i <= totalPages; i++) {
            Slice<SubscriptionHistory> slice = subscriptionRepository.findAllByMonthlyPaymentDay(days, PageRequest.of(0, 100));
            slice.getContent().stream().forEach(subscription -> {
                try {
                    renew(subscription);
                } catch (Exception e) {
                    cancel(subscription.getUser().getId());
                    log.info("결제중 오류 발생으로 환불 및 구독 취소");
                }
            });
        }
    }
}
