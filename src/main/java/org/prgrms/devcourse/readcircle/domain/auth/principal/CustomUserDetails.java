package org.prgrms.devcourse.readcircle.domain.auth.principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {
    private final String userId;
    private final Collection<? extends GrantedAuthority> authorities;



    public CustomUserDetails(String userId, Collection<? extends GrantedAuthority> authorities ) {
        this.userId = userId;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return userId;
    }
}
