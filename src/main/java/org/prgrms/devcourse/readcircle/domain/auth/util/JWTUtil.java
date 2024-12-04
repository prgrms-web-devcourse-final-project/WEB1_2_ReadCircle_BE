package org.prgrms.devcourse.readcircle.domain.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {
    @Value("${jwt.secret.key}")
    private String key;

    private SecretKey getSecretKey() {
        try {
            return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("비밀 키를 생성하는 중 오류 발생", e);
        }
    }

    public String createToken(Map<String, Object> valueMap, int min) {
        SecretKey secretKey = getSecretKey();  // 비밀 키 얻기

        Date now = new Date();  // 토큰 발행 시간

        // JWT 토큰 생성
        return Jwts.builder()
                .header().add("alg", "HS256")//HS256알고리즘으로 헤더에 alg필드로 추가
                .add("type", "JWT")//헤더에 타입 필드를 추가하고 값으로 jwt 설정
                .and()
                .issuedAt(now)       //토큰 발행 시간
                .expiration(         //토큰 만료 시간
                        new Date( now.getTime() + Duration.ofMinutes(min).toMillis()) )
                .claims(valueMap)             // 페이로드에 데이터 추가
                .signWith(secretKey)             // 서명
                .compact();                     // 토큰 문자열 반환
    }

    // JWT 토큰 검증 메서드
    public Map<String, Object> validateToken(String token) {
        SecretKey secretKey = getSecretKey();  // 비밀 키 얻기

        try {
            // JWT 파싱 및 검증
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token).getPayload();             // 페이로드 추출

            if (claims.getExpiration() != null) {
                log.info("Expiration Time: " + claims.getExpiration());
            } else {
                log.warn("Expiration time not found in claims.");
            }

            log.info("--- claim " + claims);
            return claims;
        } catch (ExpiredJwtException e) {
            // 만료된 토큰 처리
            log.error("Expired JWT token: " + e.getMessage());
            throw new RuntimeException("Token has expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰 검증 실패 시 처리 (만료, 서명 불일치 등)
            log.error("Invalid JWT token: " + e.getMessage());
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
