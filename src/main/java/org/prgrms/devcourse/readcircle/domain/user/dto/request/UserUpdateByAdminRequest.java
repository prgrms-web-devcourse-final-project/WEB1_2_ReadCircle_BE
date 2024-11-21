package org.prgrms.devcourse.readcircle.domain.user.dto.request;

import lombok.Getter;
import org.prgrms.devcourse.readcircle.domain.user.value.Role;

@Getter
public class UserUpdateByAdminRequest {
    private String nickname;

    private String email;

    private Role role;

    private String password;

}
