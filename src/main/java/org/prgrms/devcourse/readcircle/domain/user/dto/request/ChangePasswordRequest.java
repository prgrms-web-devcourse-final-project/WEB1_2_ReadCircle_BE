package org.prgrms.devcourse.readcircle.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    private String currentPassword;
    @NotBlank(message = "변경하실 비밀번호를 입력하세요")
    private String newPassword;
}
