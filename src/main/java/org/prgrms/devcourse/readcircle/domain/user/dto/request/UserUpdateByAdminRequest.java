package org.prgrms.devcourse.readcircle.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.domain.user.entity.enums.Role;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 필드는 무시
public class UserUpdateByAdminRequest {
    private String nickname;

    private String email;

    private String address;

    private Role role;

    private String password;

}
