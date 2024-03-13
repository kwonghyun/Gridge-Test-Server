package com.example.demo.src.user;


import com.example.demo.common.Constant;
import com.example.demo.common.entity.OAuth;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.oauth.OAuthService;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.response.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final OAuthService oAuthService;
    private final JwtService jwtService;


    //POST
    @Transactional
    public User createUser(PostUserReq postUserReq) {
        //중복 체크
        Optional<User> checkUser = userRepository.findByLoginIdAndState(postUserReq.getLoginId(), ACTIVE);
        if(checkUser.isPresent() == true){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(encryptPwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        User saveUser = userRepository.save(postUserReq.toUserEntity());
        termsRepository.save(postUserReq.toTermsEntity(saveUser));
        return saveUser;

    }

    @Transactional
    public PostUserRes createNormalUser(PostUserReq postUserReq) {
        //중복 체크
        User newUser = createUser(postUserReq);
        return new PostUserRes(newUser.getId());
    }

    @Transactional
    public PostUserRes createOAuthUser(PostUserReq postUserReq, String oAuthAccessToken) throws JsonProcessingException {
        User newUser = createUser(postUserReq);
        OAuth oAuth = oAuthService.createOAuth(Constant.SocialLoginType.KAKAO, oAuthAccessToken, newUser);

        return new PostUserRes(newUser.getId());
    }

    @Transactional
    public void modifyUserName(Long userId, PatchUserReq patchUserReq) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.updateName(patchUserReq.getName());
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.delete();

        if (user.isOAuth()) {
            oAuthService.deleteByUserId(userId);
        }
    }

    public List<GetUserRes> getUsers() {
        List<GetUserRes> getUserResList = userRepository.findAllByState(ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }

    public List<GetUserRes> getUsersByLoginId(String loginId) {
        List<GetUserRes> getUserResList = userRepository.findAllByLoginIdAndState(loginId, ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }

    public GetUserRes getUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserRes(user);
    }


    @Transactional
    public PostLoginRes logIn(PostLoginReq postLoginReq) {
        User user = userRepository.findByLoginIdAndState(postLoginReq.getLoginId(), ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            user.updateLastLoginAt();
            String jwt = jwtService.createJwt(user.getId(), user.getAuthority());
            return new PostLoginRes(jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

}
