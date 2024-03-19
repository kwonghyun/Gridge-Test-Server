package com.example.demo.utils;


import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.user.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.demo.common.response.BaseResponseStatus.EMPTY_JWT;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret-key}")
    private String JWT_SECRET_KEY;

    /*
    JWT 생성
    @param userId
    @return String
     */
    public String createJwt(Long userId, String username, User.UserAuthority authority){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId", userId)
                .claim("authority", authority.name())
                .claim("username", username)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("X-ACCESS-TOKEN");
        if (jwt == null || jwt.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }
        return jwt;
    }

    public void validateJwt() {
        String jwt = getJwt();
        parseClaims(jwt);
    }

    public Jws<Claims> parseClaims(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(jwt);
        } catch (SignatureException | MalformedJwtException e) {
            log.info("JWT 서명이 잘못되었습니다.");
            throw new BaseException(BaseResponseStatus.INVALID_SIGNATURE_JWT);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료되었습니다.");
            throw new BaseException(BaseResponseStatus.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰입니다.");
            throw new BaseException(BaseResponseStatus.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 토큰입니다.");
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }
    }

    /*
    JWT에서 userId 추출
    @return Long
    @throws BaseException
     */
    public Long getUserId() {

        String accessToken = getJwt();
        Jws<Claims> claims = parseClaims(accessToken);
        return claims.getBody().get("userId", Long.class);
    }

    public Long getUserId(String accessToken) {
        Jws<Claims> claims = parseClaims(accessToken);
        return claims.getBody().get("userId", Long.class);
    }

    public String getUsername() {
        String accessToken = getJwt();
        Jws<Claims> claims = parseClaims(accessToken);
        return claims.getBody().get("username", String.class);
    }

    public String getUsername(String accessToken) {
        Jws<Claims> claims = parseClaims(accessToken);
        return claims.getBody().get("username", String.class);
    }

}
