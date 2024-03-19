package com.example.demo.src.subscription;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.subscription.model.PortOneWebhookReq;
import com.example.demo.src.subscription.model.PostPaymentRes;
import com.example.demo.src.subscription.model.PostPaymentResultReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("app/payments")
@Slf4j
@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final JwtService jwtService;
    private final PortOneService portOneService;

    @PostMapping
    public BaseResponse<PostPaymentRes> createPayment() {
        Long userId = jwtService.getUserId();
        PostPaymentRes payment = paymentService.createPayment(userId);
        return new BaseResponse<>(payment);
    }

    @PostMapping("result")
    public BaseResponse checkResult(
            @RequestBody PostPaymentResultReq req
    ) {
        Long userId = jwtService.getUserId();
        log.info("POST result 호출 : {}", req);
        paymentService.saveResult(req);
        return new BaseResponse("결과 저장 완료");
    }

    @PostMapping("port-one-webhook")
    @ResponseBody
    public BaseResponse webhook(@RequestBody PortOneWebhookReq req) {
        log.info(req.toString());
        paymentService.saveWebhookResult(req);

        return new BaseResponse("일치");
    }

}
