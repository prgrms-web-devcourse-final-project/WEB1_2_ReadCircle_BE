package org.prgrms.devcourse.readcircle.domain.user.value;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum Role implements GrantedAuthority {
    USER,
    MODERATOR,  // 게시판 중간 관리자
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();  // Spring Security는 "ROLE_" 접두사를 붙인 권한명을 기대합니다.
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (this == ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (this == MODERATOR) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }
}
