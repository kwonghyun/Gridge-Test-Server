package com.example.demo.src.user;


import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.oauth.entity.OAuth;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demo.common.response.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final JwtService jwtService;


    //POST
    @Transactional
    public User createUser(PostUserReq postUserReq) {
        //중복 체크
        Optional<User> checkUser = userRepository.findActiveUserByLoginId(postUserReq.getLoginId());
        if(checkUser.isPresent() == true){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
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
        user.delete();
        Optional<OAuth> optionalOAuth = Optional.ofNullable(user.getOAuth());
        optionalOAuth.ifPresent(oAuth -> oAuth.delete());
    }

    @Transactional
    public PostLoginRes login(PostLoginReq postLoginReq) {
        User user = userRepository.findActiveUserByLoginId(postLoginReq.getLoginId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        String encryptPwd;
        try {
            encryptPwd = SHA256.encrypt(postLoginReq.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            user.updateLastLoginAt();
            String jwt = jwtService.createJwt(user.getId(), user.getName(), user.getAuthority());
            return new PostLoginRes(jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

}
