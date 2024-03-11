package com.example.demo.common.auth;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.exceptions.FilterExceptionHandler;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.user.entity.User;
import com.example.demo.utils.JwtService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // request Header에서 jwt 찾기
        String accessToken;
        try {
            accessToken = jwtService.getJwt();
        } catch (BaseException e) {
            log.info("Authorization Header 없음 미인증 사용자 요청");
            filterChain.doFilter(request, response);
            return;
        }
        validateToken();
        Authentication authentication;

        log.info("access token에서 인증 객체 생성 시작");

        Claims claims = jwtService.parseClaims();
        User user = jwtService.generateUserFromClaims(claims);

        authentication = new CustomAuthenticationToken(user, accessToken, user.getAuthorities());


        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        log.info("{} 인증 객체 생성 완료", context.getAuthentication().getName());


        filterChain.doFilter(request, response);
    }

    private void validateToken() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        log.info("token 검증 시작");
        try {
            jwtService.parseClaims();
        } catch (SignatureException | MalformedJwtException e) {
            log.info("JWT 서명이 잘못되었습니다.");
            FilterExceptionHandler.jwtExceptionHandler(response, BaseResponseStatus.INVALID_SIGNATURE_JWT);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료되었습니다.");
            FilterExceptionHandler.jwtExceptionHandler(response, BaseResponseStatus.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰입니다.");
            FilterExceptionHandler.jwtExceptionHandler(response, BaseResponseStatus.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 토큰입니다.");
            FilterExceptionHandler.jwtExceptionHandler(response, BaseResponseStatus.INVALID_JWT);
        }
    }
}