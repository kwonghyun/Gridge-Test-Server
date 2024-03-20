package com.example.demo.common.config;

import com.example.demo.src.subscription.repository.BillingKeyRepository;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TableInitialInsertion {
    private final UserRepository userRepository;
    private final BillingKeyRepository billingKeyRepository;

    @PostConstruct
    public void init() {
        User user = userRepository.save(
                User.builder()
                        .loginId("qwerqwer")
                        .password(SHA256.encrypt("qwerqwer1!"))
                        .name("테스트유저")
                        .phoneNumber("010-0000-0000")
                        .birthDay(LocalDate.parse("1999-12-12"))
                        .build()
        );
    }
}
