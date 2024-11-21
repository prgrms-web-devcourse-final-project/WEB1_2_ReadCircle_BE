package org.prgrms.devcourse.readcircle.domain.user.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.value.Role;

@Getter
public class UserSignUpRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;

    @NotBlank(message = "패스워드는 필수입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^(?=.{1,100}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String profileImageUrl;

    public User toEntity(String encodedPassword, String profileImageUrl) {
        return User.builder()
                .userId(userId)
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .role(Role.USER) //등록시에 USER로 자동설정
                .build();
    }


}
