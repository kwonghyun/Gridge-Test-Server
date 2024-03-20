package com.example.demo.src.subscription.service;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.subscription.entity.BillingKey;
import com.example.demo.src.subscription.entity.PaymentHistory;
import com.example.demo.src.subscription.entity.PaymentResult;
import com.example.demo.src.subscription.model.*;
import com.example.demo.src.subscription.repository.BillingKeyRepository;
import com.example.demo.src.subscription.repository.PaymentRepository;
import com.example.demo.src.subscription.repository.PaymentResultRepository;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentResultRepository paymentResultRepository;
    private final PortOneService portOneService;
    private final UserRepository userRepository;
    private final BillingKeyRepository billingKeyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final int monthlyPrice = 100;

    @Transactional
    public PostPaymentRes createPaymentForBillingKey(Long userId) {
        User user = userRepository.findActiveUserById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        if (user.getUserState() != User.UserState.ACTIVE) {
            throw new BaseException(BaseResponseStatus.NOT_FIND_USER);
        }
        PaymentHistory paymentHistory = paymentRepository.save(
                PaymentHistory.builder()
                        .merchantUid(userId + "_sub_" + System.currentTimeMillis())
                        .price(monthlyPrice)
                        .type(PaymentHistory.Type.BILLING_KEY)
                        .user(user)
                        .build()
        );
        return PostPaymentRes.builder()
                .customerUid(userId + "_" + UUID.randomUUID().toString().substring(0, 10))
                .merchantId(paymentHistory.getMerchantUid())
                .amount(monthlyPrice)
                .build();
    }

    @Transactional
    public PostBillingKeyPaymentReq createPaymentForPay(
            Long userId,
            PaymentHistory.Type paymentType
    ) {
        User user = userRepository.findActiveUserWithBillingKeyById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        if (user.getUserState() != User.UserState.ACTIVE) {
            throw new BaseException(BaseResponseStatus.NOT_FIND_USER);
        }
        if (user.getBillingKey() == null || user.getBillingKey().size() == 0) {
            throw new BaseException(BaseResponseStatus.NOT_FIND_BILLING_KEY);
        }
        BillingKey billingKey = user.getBillingKey().get(0);
        PaymentHistory paymentHistory = paymentRepository.save(
                PaymentHistory.builder()
                        .merchantUid(userId + "_sub_" + System.currentTimeMillis())
                        .price(monthlyPrice)
                        .type(paymentType)
                        .billingKey(billingKey)
                        .user(user)
                        .build()
        );
        return PostBillingKeyPaymentReq.builder()
                .merchantUid(paymentHistory.getMerchantUid())
                .customerUid(billingKey.getCustomerUid())
                .amount(paymentHistory.getPrice())
                .build();
    }

    @Transactional
    public PaymentResult saveBillingKeyPaymentResult(PostBillingKeyPaymentRes postBillingKeyPaymentRes, String merchantUid) {
        PaymentHistory paymentHistory = paymentRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_PAYMENT));

        if (postBillingKeyPaymentRes.getCode() != 0) {
             paymentResultRepository.save(
                     PaymentResult.builder()
                    .paymentHistory(paymentHistory)
                    .message(postBillingKeyPaymentRes.getMessage())
                    .payState(PaymentResult.PayState.FAILED)
                    .build()
             );
            throw new BaseException(BaseResponseStatus.FAILED_PAYMENT);
        } else if (postBillingKeyPaymentRes.getResponse().getFailReason() != null) {
            paymentResultRepository.save(
                    PaymentResult.builder()
                            .paymentHistory(paymentHistory)
                            .impUid(postBillingKeyPaymentRes.getResponse().getImpUid())
                            .message(postBillingKeyPaymentRes.getResponse().getFailReason())
                            .payState(PaymentResult.PayState.FAILED)
                            .build()
            );
            throw new BaseException(BaseResponseStatus.FAILED_PAYMENT);
        } else {
            return paymentResultRepository.save(
                    PaymentResult.builder()
                            .paymentHistory(paymentHistory)
                            .impUid(postBillingKeyPaymentRes.getResponse().getImpUid())
                            .message(postBillingKeyPaymentRes.getResponse().getReceiptUrl())
                            .payState(PaymentResult.PayState.PAID)
                            .build()
            );
        }
    }

    @Transactional
    public PostCancellationReq createPaymentForCancellation(String impUid) {
        PaymentResult paymentResult = paymentResultRepository.findByImpUid(impUid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_PAYMENT));
        User user = paymentResult.getPaymentHistory().getUser();
        Long userId = user.getId();

        PaymentHistory paymentHistory = paymentRepository.save(
                PaymentHistory.builder()
                        .merchantUid(userId + "_sub_" + System.currentTimeMillis())
                        .price(monthlyPrice)
                        .impUidToCancel(impUid)
                        .type(PaymentHistory.Type.CANCELLATION)
                        .user(user)
                        .build()
        );
        return new PostCancellationReq(impUid);
    }



    @Transactional
    public void saveWebhookResult(PortOneWebhookReq req) {
        PaymentHistory paymentHistory = paymentRepository.findByMerchantUid(req.getMerchantUid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_PAYMENT));
        if (paymentHistory.getPaymentResult() == null) return;

        PaymentResult.PayState state = PaymentResult.PayState.valueOf(req.getStatus().toUpperCase());
        PaymentResult paymentResult;
        if (state == PaymentResult.PayState.CANCELLED) {
            paymentResult = PaymentResult.builder()
                    .cancellationId(req.getMerchantUid())
                    .impUid(req.getImpUid())
                    .payState(state)
                    .paymentHistory(paymentHistory)
                    .build();

        } else {
            paymentResult = PaymentResult.builder()
                    .impUid(req.getImpUid())
                    .payState(state)
                    .paymentHistory(paymentHistory)
                    .build();
        }
        paymentResultRepository.save(paymentResult);
    }

    @Transactional
    public void firstPay(PostPaymentResultReq req, Long userId) {
        validateIssuesBillingKey(req);
        PostBillingKeyPaymentReq postBillingKeyPaymentReq
                = createPaymentForPay(userId, PaymentHistory.Type.FIRST_PAYMENT);

        PostBillingKeyPaymentRes postBillingKeyPaymentRes
                = portOneService.executePayment(postBillingKeyPaymentReq);

        PaymentResult paymentResult = saveBillingKeyPaymentResult(postBillingKeyPaymentRes, postBillingKeyPaymentReq.getMerchantUid());
        applicationEventPublisher.publishEvent(new FirstPaymentSuccessEvent(paymentResult));
    }

    // TODO 스케줄링으로 정기결제

    @Transactional
    public PaymentResult monthlyPay(Long userId) {
        PostBillingKeyPaymentReq postBillingKeyPaymentReq
                = createPaymentForPay(userId, PaymentHistory.Type.SCHEDULED_PAYMENT);

        PostBillingKeyPaymentRes postBillingKeyPaymentRes
                = portOneService.executePayment(postBillingKeyPaymentReq);

        return saveBillingKeyPaymentResult(postBillingKeyPaymentRes, postBillingKeyPaymentReq.getMerchantUid());
    }

    @Transactional
    public PaymentResult cancelPay(String impUid) {
        PostCancellationReq req = createPaymentForCancellation(impUid);
        PostCancellationRes postCancellationRes = portOneService.cancelPayment(req);
        PaymentResult paymentResult = saveCancellationResult(postCancellationRes, impUid);
        deleteBillingKey(impUid);
        return paymentResult;
    }

    @Transactional
    public void deleteBillingKey(String impUid) {
        PaymentResult paymentResult = paymentResultRepository.findByImpUid(impUid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_PAYMENT));
        BillingKey billingKey = paymentResult.getPaymentHistory().getBillingKey();
        billingKeyRepository.delete(billingKey);
    }

    @Transactional
    public PaymentResult saveCancellationResult(PostCancellationRes postCancellationRes, String impUid) {
        PaymentHistory paymentHistory = paymentRepository.findByImpUidToCancel(impUid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_PAYMENT));

        if (postCancellationRes.getCode() != 0) {
            paymentResultRepository.save(
                    PaymentResult.builder()
                            .paymentHistory(paymentHistory)
                            .message(postCancellationRes.getMessage())
                            .payState(PaymentResult.PayState.FAILED)
                            .build()
            );
            throw new BaseException(BaseResponseStatus.FAILED_PAYMENT_CANCELLATION);
        } else if (postCancellationRes.getResponse().getFailReason() != null) {
            paymentResultRepository.save(
                    PaymentResult.builder()
                            .paymentHistory(paymentHistory)
                            .impUid(postCancellationRes.getResponse().getImpUid())
                            .message((String) postCancellationRes.getResponse().getFailReason())
                            .payState(PaymentResult.PayState.FAILED)
                            .build()
            );
            throw new BaseException(BaseResponseStatus.FAILED_PAYMENT_CANCELLATION);
        } else {
            return paymentResultRepository.save(
                    PaymentResult.builder()
                            .paymentHistory(paymentHistory)
                            .impUid(postCancellationRes.getResponse().getImpUid())
                            .cancellationId(postCancellationRes.getResponse().getCancelHistory()[0].getCancellationId())
                            .message(postCancellationRes.getResponse().getCancelReceiptUrls()[0])
                            .payState(PaymentResult.PayState.PAID)
                            .build()
            );
        }

    }
    @Transactional
    public void validateIssuesBillingKey(PostPaymentResultReq req) {
        PaymentHistory paymentHistory = paymentRepository.findByMerchantUid(req.getMerchantUid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_PAYMENT));

        PaymentResult paymentResult;
        if (req.getSuccess()) {
            if (
                    portOneService.isCustomerUidValid(req.getCustomerUid())
                    && req.getMerchantUid().equals(paymentHistory.getMerchantUid())
                    && req.getCurrency().equals("KRW")
                    && req.getPaidAmount().equals(paymentHistory.getPrice())
            ) {
                log.info("검증 성공");
                paymentResultRepository.save(
                        PaymentResult.builder()
                        .impUid(req.getImpUid())
                        .payState(PaymentResult.PayState.valueOf(req.getStatus().toUpperCase()))
                        .paymentHistory(paymentHistory)
                        .build()
                );
                billingKeyRepository.save(
                        BillingKey.builder()
                                .user(paymentHistory.getUser())
                                .customerUid(req.getCustomerUid())
                                .build()
                );
            } else {
                log.info("검증 실패");
                paymentResultRepository.save(PaymentResult.builder()
                        .impUid(req.getImpUid())
                        .payState(PaymentResult.PayState.FAILED)
                        .message("customerUid 등록 안됨")
                        .paymentHistory(paymentHistory)
                        .build()
                );
                throw new BaseException(BaseResponseStatus.FAKE_PAYMENT_INFO);
            }
        } else {
            paymentResultRepository.save(PaymentResult.builder()
                    .impUid(req.getImpUid())
                    .payState(PaymentResult.PayState.FAILED)
                    .message(req.getErrorMsg())
                    .paymentHistory(paymentHistory)
                    .build()
            );
            throw new BaseException(BaseResponseStatus.ISSUE_BILLING_KEY_FAILED);
        }
    }
}
