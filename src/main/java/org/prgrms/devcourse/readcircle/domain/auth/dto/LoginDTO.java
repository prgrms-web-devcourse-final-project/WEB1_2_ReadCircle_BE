package org.prgrms.devcourse.readcircle.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDTO {
    @NotBlank(message = "아이디를 입력하세요.")
    private String userId;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;


}
