package com.example.demo.src.user;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.oauth.entity.OAuth;
import com.example.demo.src.subscription.model.UserDeletedEvent;
import com.example.demo.src.user.entity.Terms;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.PostConsentTermsReq;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.demo.common.response.BaseResponseStatus.*;
import static com.example.demo.src.user.entity.User.UserState.*;

// Service Create, Update, Delete 의 로직 처리
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final JwtService jwtService;
    private final ApplicationEventPublisher applicationEventPublisher;


    //POST
    @Transactional
    public User createUser(PostUserReq postUserReq) {
        //중복 체크
        Optional<User> checkUser = userRepository.findActiveUserByLoginId(postUserReq.getLoginId());
        if(checkUser.isPresent() == true){
            throw new BaseException(DUPLICATED_LOGIN_ID);
        }
        String encryptPwd;
        try {
            encryptPwd = SHA256.encrypt(postUserReq.getPassword());
            postUserReq.setPassword(encryptPwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        User saveUser = userRepository.save(postUserReq.toUserEntity());
        termsRepository.save(postUserReq.toTermsEntity(saveUser));
        return saveUser;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findActiveUserById(userId)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        // 구독 되어있으면 취소
        applicationEventPublisher.publishEvent(new UserDeletedEvent(user));

        Optional<Terms> optionalTerms = termsRepository.findByUserId(userId);
        if (optionalTerms.isPresent()) optionalTerms.get().delete();

        Optional<OAuth> optionalOAuth = Optional.ofNullable(user.getOAuth().get(0));
        optionalOAuth.ifPresent(oAuth -> oAuth.delete());

        user.delete();
    }

    @Transactional
    public PostLoginRes login(PostLoginReq postLoginReq) {
        User user = userRepository.findUserByLoginId(postLoginReq.getLoginId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        String encryptPwd;
        try {
            encryptPwd = SHA256.encrypt(postLoginReq.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            if (user.getState() == BaseEntity.State.INACTIVE) throw new BaseException(INACTIVE_USER);
            if (user.getUserState() == BANNED) throw new BaseException(BANNED_USER);
            if (user.getUserState() == DORMANT) throw new BaseException(DORMANT_USER);
            user.updateLastLoginAt();
            String jwt = jwtService.createJwt(user.getId(), user.getName(), user.getAuthority());
            return new PostLoginRes(jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    // 휴면계정 휴면 해제 로그인
    @Transactional
    public PostLoginRes consentTermsLogin(PostConsentTermsReq req) {
        User user = userRepository.findUserByLoginId(req.getLoginId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        String encryptPwd;
        try {
            encryptPwd = SHA256.encrypt(req.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            if (user.getState() == BaseEntity.State.INACTIVE) throw new BaseException(INACTIVE_USER);
            if (user.getUserState() == BANNED) throw new BaseException(BANNED_USER);
            if (
                    user.getUserState() == DORMANT
            ) {
                user.updateUserState(ACTIVE);
                Terms terms = termsRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new BaseException(NOT_FIND_USER));
                terms.updateConsent(
                        req.getDataTermsConsent(), req.getUsageTermsConsent(), req.getLocationTermsConsent()
                );
                user.updateLastLoginAt();
                String jwt = jwtService.createJwt(user.getId(), user.getName(), user.getAuthority());
                return new PostLoginRes(jwt);
            } else {
                throw new BaseException(CONSENT_REQUIRED);
            }

        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    // 매일 오전 3시에 휴면 계정 업데이트
    @Scheduled(cron = "0 3 * * *")
    @Transactional
    public void updateDormantUser() {
        LocalDate dateBefore364Days = LocalDate.now().minusDays(364L);
        List<Terms> terms = termsRepository.findAllByConsentDate(dateBefore364Days);

        terms.stream().forEach(term -> {
            term.expire();
            term.getUser().updateUserState(DORMANT);
        });
    }
}
