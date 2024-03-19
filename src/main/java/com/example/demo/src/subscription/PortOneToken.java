package com.example.demo.src.subscription;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PortOneToken {
    private String accessToken;
    private long issuedAt;
    private long expiredAt;
}
