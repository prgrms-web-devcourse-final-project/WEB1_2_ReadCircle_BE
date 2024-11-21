package org.prgrms.devcourse.readcircle.domain.user.dto.response;

import lombok.Getter;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.value.Role;

import java.time.LocalDateTime;

@Getter
public class UserInfoByAdminResponse {
    private final String userId;
    private final String email;
    private final String nickname;
    private final Role role;
    private final String profileImage;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserInfoByAdminResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.profileImage = user.getProfileImageUrl();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}

