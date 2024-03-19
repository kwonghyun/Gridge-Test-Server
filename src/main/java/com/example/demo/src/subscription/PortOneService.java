package com.example.demo.src.subscription;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.subscription.entity.PaymentHistory;
import com.example.demo.src.subscription.model.GetCustomerUidRes;
import com.example.demo.src.subscription.model.PostPaymentRes;
import com.example.demo.src.subscription.model.PostPortOneTokenRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PortOneService {

    private final String portOneRestApiKey;
    private final String portOneSecret;
    private final String portOneApiHostname;

    private final PortOneToken portOneToken;

    public PortOneService(@Value("${portOne.restApiKey}") String portOneRestApiKey,
                          @Value("${portOne.secret}") String portOneSecret,
                          @Value("${portOne.hostname}") String portOneApiHostname,
                          PortOneToken portOneToken
                          ) {
        this.portOneRestApiKey = portOneRestApiKey;
        this.portOneSecret = portOneSecret;
        this.portOneToken = portOneToken;
        this.portOneApiHostname = portOneApiHostname;
    }


    public PortOneToken requestPortOneToken() {
        log.info("포트원에 토큰 요청");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("imp_key", portOneRestApiKey);
        params.put("imp_secret", portOneSecret);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
        log.info(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                portOneApiHostname + "/users/getToken",
                request, String.class
        );
        log.info(response.getBody());
        PostPortOneTokenRes postPortOneTokenRes;
        try {
            postPortOneTokenRes = objectMapper.readValue(response.getBody(), PostPortOneTokenRes.class);

        } catch (JsonProcessingException e) {
            log.info("포트원 토큰 파싱 에러");
            throw new BaseException(BaseResponseStatus.GET_PORT_ONE_TOKEN_FAIL);
        }
        if (postPortOneTokenRes.getCode() == 0) {
            portOneToken.setAccessToken("Bearer " + postPortOneTokenRes.getAccessToken());
            portOneToken.setIssuedAt(postPortOneTokenRes.getNow());
            portOneToken.setExpiredAt(portOneToken.getExpiredAt());
            return portOneToken;
        } else {
            throw new BaseException(BaseResponseStatus.GET_PORT_ONE_TOKEN_FAIL);
        }

    }

    public boolean isCustomerUidValid(String customerUid) {
        log.info("customerUid 검증 : {}", customerUid);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", getPortOneAccessToken());
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                portOneApiHostname + "/subscribe/customers/" + customerUid,
                HttpMethod.GET,
                entity, String.class
        );
        log.info(response.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        GetCustomerUidRes getCustomerUidRes;
        try {
            getCustomerUidRes = objectMapper.readValue(response.getBody(), GetCustomerUidRes.class);
        } catch (JsonProcessingException e) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
        if (getCustomerUidRes.getCode() == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isPortOneTokenValid() {
        return portOneToken.getAccessToken() != null
                && portOneToken.getExpiredAt() > (System.currentTimeMillis() / 1000  + 5 * 60L);
    }

    public String getPortOneAccessToken(){
        if (isPortOneTokenValid()) {
            return portOneToken.getAccessToken();
        } else {
            return requestPortOneToken().getAccessToken();
        }
    }
    public PostPaymentRes getPostBillingKeyReq(PaymentHistory paymentHistory) {
        return new PostPaymentRes(paymentHistory);
    }

}
