package com.example.demo.src.subscription;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.subscription.entity.PaymentHistory;
import com.example.demo.src.subscription.entity.PaymentResult;
import com.example.demo.src.subscription.model.PortOneWebhookReq;
import com.example.demo.src.subscription.model.PostPaymentRes;
import com.example.demo.src.subscription.model.PostPaymentResultReq;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentResultRepository paymentResultRepository;
    private final PortOneService portOneService;
    private final UserRepository userRepository;
    private final int monthlyPrice = 100;

    @Transactional
    public PostPaymentRes createPayment(Long userId) {
        User user = userRepository.findActiveUserById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        if (user.getUserState() != User.UserState.ACTIVE) {
            throw new BaseException(BaseResponseStatus.NOT_FIND_USER);
        }
        PaymentHistory paymentHistory = paymentRepository.save(
                PaymentHistory.builder()
                        .merchantUid(userId + "_sub_" + System.currentTimeMillis())
                        .customerUid(userId + "_" + System.currentTimeMillis())
                        .price(monthlyPrice)
                        .type(PaymentHistory.Type.BILLING_KEY)
                        .paymentState(PaymentHistory.PaymentState.STAGED)
                        .user(user)
                        .build()
        );

        return new PostPaymentRes(paymentHistory);
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
    public void saveResult(PostPaymentResultReq req) {
        PaymentHistory paymentHistory = paymentRepository.findByMerchantUid(req.getMerchantUid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_PAYMENT));

        PaymentResult paymentResult;
        if (req.getSuccess()) {
            if (
                    portOneService.isCustomerUidValid(req.getCustomerUid())
                    && req.getCurrency() == "KRW"
                    && req.getPaidAmount() == paymentHistory.getPrice()
            ) {
                log.info("검증 성공");
                paymentResultRepository.save(
                        PaymentResult.builder()
                        .impUid(req.getImpUid())
                        .payState(PaymentResult.PayState.valueOf(req.getStatus().toUpperCase()))
                        .paymentHistory(paymentHistory)
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
            }
            throw new BaseException(BaseResponseStatus.FAKE_PAYMENT_INFO);
        } else {
            paymentResultRepository.save(PaymentResult.builder()
                    .impUid(req.getImpUid())
                    .payState(PaymentResult.PayState.FAILED)
                    .message(req.getErrorMsg())
                    .paymentHistory(paymentHistory)
                    .build()
            );
        }
    }
}
