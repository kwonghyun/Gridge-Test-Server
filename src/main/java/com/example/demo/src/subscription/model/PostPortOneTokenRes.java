package com.example.demo.src.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PostPortOneTokenRes {
    private Integer code;
    private String message;
    private Response response;

    @Getter
    public static class Response {
        @JsonProperty("access_token")
        String accessToken;
        Long now;
        @JsonProperty("expired_at")
        Long expiredAt;
    }

    public String getAccessToken() {
        return this.response.accessToken;
    }

    public Long getNow() {
        return this.response.now;
    }

    public Long getExp() {
        return this.response.expiredAt;
    }

}
