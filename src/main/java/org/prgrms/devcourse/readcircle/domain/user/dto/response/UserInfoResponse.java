package org.prgrms.devcourse.readcircle.domain.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private final String userId;
    private final String email;
    private final String nickname;
    private final String profileImageUrl;

    public UserInfoResponse(User user, String uploadPath) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImageUrl = uploadPath + "/" + user.getProfileImageUrl();  // 경로 + 파일명
    }
}