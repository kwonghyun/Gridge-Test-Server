package com.example.demo.src.oauth.model;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;


//구글에 일회성 코드를 다시 보내 받아올 액세스 토큰을 포함한 JSON 문자열을 담을 클래스
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoOAuthToken {
    private String access_token;
    private int expires_in;
    private String scope;
    private String token_type;
    private String id_token;

    public String extractEmail() {
        String payload = id_token.split("\\.")[1];
        String decodedPayload;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            decodedPayload = new String(Base64.decodeBase64(payload), "UTF-8");
            JsonNode jsonNode = objectMapper.readTree(decodedPayload);

            String email = jsonNode.get("email").asText();
            return email;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }
    }
}
