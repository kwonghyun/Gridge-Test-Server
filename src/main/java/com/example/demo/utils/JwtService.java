package com.example.demo.utils;


import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.demo.common.response.BaseResponseStatus.EMPTY_JWT;
import static com.example.demo.common.response.BaseResponseStatus.INVALID_JWT;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String JWT_SECRET_KEY;

    /*
    JWT 생성
    @param userId
    @return String
     */
    public String createJwt(Long userId, User.UserAuthority authority){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId", userId)
                .claim("authority", authority.name())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 Authentication 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.split(" ")[1];
        } else {
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }
    }

    public Jws<Claims> parseClaims(String jwt) {
        try{
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(jwt);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }
    }
    /*
    JWT에서 userId 추출
    @return Long
    @throws BaseException
     */
    public User getUser() throws BaseException{
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }
        // 2. JWT parsing
        Jws<Claims> claims = parseClaims(accessToken);

        // 3. userIdx 추출
        return User.builder()
                .id(claims.getBody().get("userId", Long.class))
                .authority(User.UserAuthority.USER)
                .build();
    }

}
