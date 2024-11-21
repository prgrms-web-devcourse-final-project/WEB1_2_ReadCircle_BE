package org.prgrms.devcourse.readcircle.domain.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.common.util.CookieUtil;
import org.prgrms.devcourse.readcircle.domain.auth.dto.LoginDTO;
import org.prgrms.devcourse.readcircle.domain.auth.util.JWTUtil;
import org.prgrms.devcourse.readcircle.domain.user.dto.UserDTO;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.exception.UserException;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    private static final int ACCESS_TOKEN_EXPIRATION = 120; // 2시간
    private static final int REFRESH_TOKEN_EXPIRATION = 60 * 24 * 7; // 7일
    private static final int ACCESS_TOKEN_COOKIE_EXPIRATION = 3600; // 1시간 (쿠키 만료 시간)
    private static final int REFRESH_TOKEN_COOKIE_EXPIRATION = 604800; // 7일 (쿠키 만료 시간)

    public Map<String, String> login(LoginDTO loginDTO, HttpServletResponse response) {
        // 1. 사용자 검증
        User user = userRepository.findByUserId(loginDTO.getUserId())
                .orElseThrow(UserException.NOT_FOUND::get);

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw UserException.BAD_CREDENTIALS.get();
        }

        // 2. 사용자 정보 가져오기
        UserDTO foundUserDTO = new UserDTO(user);

        // 3. 토큰 생성
        Map<String, Object> payloadMap = foundUserDTO.getPayload();
        String accessToken = jwtUtil.createToken(payloadMap, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = jwtUtil.createToken(Map.of("userId", foundUserDTO.getUserId()), REFRESH_TOKEN_EXPIRATION);

        // 4. 쿠키에 토큰 저장
        CookieUtil.addCookie(response, "accessToken", accessToken, ACCESS_TOKEN_COOKIE_EXPIRATION);
        CookieUtil.addCookie(response, "refreshToken", refreshToken, REFRESH_TOKEN_COOKIE_EXPIRATION);

        log.info("--- accessToken : {}", accessToken);
        log.info("--- refreshToken : {}", refreshToken);

        // 5. 응답 데이터 반환
        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }




}
