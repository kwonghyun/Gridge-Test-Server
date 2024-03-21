package com.example.demo.common.config;

import com.example.demo.src.subscription.repository.BillingKeyRepository;
import com.example.demo.src.user.TermsRepository;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.Terms;
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
    private final TermsRepository termsRepository;

    @PostConstruct
    public void init() {
        User user = userRepository.save(
                User.builder()
                        .loginId("test11")
                        .password(SHA256.encrypt("test1."))
                        .name("테스트유저")
                        .phoneNumber("010-0000-0000")
                        .birthDay(LocalDate.parse("1999-12-12"))
                        .build()
        );

        User user2 = userRepository.save(
                User.builder()
                        .loginId("test1111")
                        .password(SHA256.encrypt("test1."))
                        .name("테스트유저")
                        .userState(User.UserState.DORMANT)
                        .phoneNumber("010-0033-0000")
                        .birthDay(LocalDate.parse("1999-12-12"))
                        .build()
        );
        termsRepository.save(
                Terms.builder()
                        .locationTermsConsent(true)
                        .usageTermsConsent(true)
                        .dataTermsConsent(true)
                        .consentDate(LocalDate.now().minusYears(1L))
                        .consentState(Terms.TermsConsentState.EXPIRED)
                        .user(user2)
                        .build()
        );
    }
}
