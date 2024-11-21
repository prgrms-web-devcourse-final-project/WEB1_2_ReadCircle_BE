package org.prgrms.devcourse.readcircle.domain.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.auth.principal.CustomUserDetails;
import org.prgrms.devcourse.readcircle.domain.auth.util.JWTUtil;
import org.prgrms.devcourse.readcircle.domain.user.value.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtCheckFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("--- shouldNotFilter()");
        log.info("--- requestURI : " + request.getRequestURI());

        // 제외할 경로 리스트
        Set<String> excludedPaths = Set.of(
                "/api/auth/login",
                "/api/users/signup"
        );

        // 요청 URI가 제외할 경로에 포함되면 필터링을 적용하지 않음
        for (String path : excludedPaths) {
            if (request.getRequestURI().startsWith(path)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("--- doFilterInternal() ");
        log.info("--- requestURI : " + request.getRequestURI());

//      -----쿠키에 토큰값을 저장하였으므로 쿠키에서 토큰값을 가져와야한다.-----
        String accessToken = getTokenFromCookies(request, "accessToken");
        log.info("--- Access Token: {}", accessToken);

//         액세스 토큰이 없으면 403 예외 발생
        if (accessToken == null) {
            handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }

        // 토큰 유효성 검증 --------------------------------------
        try{
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            log.info("--- 토큰 유효성 검증 완료 ---");

            //SecurityContext 처리 ------------------------------------------
            String userId = claims.get("userId").toString();
            String roleString = claims.get("role").toString();
            Role role = Role.valueOf(roleString);

            Set<Role> authorities = role.getAuthorities();

            authorities.forEach(authority -> log.info("beforeAuthority: " + authority.name()));

            // Spring Security의 권한 객체로 변환
            List<GrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                    .collect(Collectors.toList());

            grantedAuthorities.forEach(authority -> logger.info("Granted Authority: "+ authority.getAuthority()));

            //토큰을 이용하여 인증된 정보 저장
            setAuthentication(userId, grantedAuthorities);

            filterChain.doFilter(request, response); //검증 결과 문제가 없는 경우
        }catch (Exception e) {
            handleException(response, e);
        }

    }

    private String getTokenFromCookies(HttpServletRequest request, String tokenName) {
        if (request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(cookie -> tokenName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    private void setAuthentication(String userId, List<GrantedAuthority> grantedAuthorities) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(userId, grantedAuthorities), null, grantedAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    public void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
    }

}
