package org.prgrms.devcourse.readcircle.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 필드는 무시
public class UserUpdateRequest {
    private String nickname;

    private String address;

    @Pattern(regexp = "^(?=.{1,100}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
