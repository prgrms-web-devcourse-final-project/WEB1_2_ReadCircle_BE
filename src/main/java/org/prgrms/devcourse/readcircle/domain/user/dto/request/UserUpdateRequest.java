package org.prgrms.devcourse.readcircle.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.domain.user.value.Role;

@Getter
public class UserUpdateRequest {
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자이어야 합니다.")
    private String nickname;
    private Role role;
    private String profileImage;

}
