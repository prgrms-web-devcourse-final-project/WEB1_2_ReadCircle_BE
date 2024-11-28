package org.prgrms.devcourse.readcircle.domain.user.entity.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.EnumSet;
import java.util.Set;

public enum Role implements GrantedAuthority {
    ADMIN, USER, MODERATOR;

    // 계층 구조 정의 (ADMIN은 모든 권한, MODERATOR는 USER 권한 포함)
    public Set<Role> getAuthorities() {
        if(this == ADMIN) {
            return EnumSet.of(ADMIN, MODERATOR, USER);
        } else if(this == MODERATOR) {
            return EnumSet.of(MODERATOR, USER);
        } else {
            return EnumSet.of(USER);
        }
    }


    @Override
    public String getAuthority() {
        return "";
    }
}
