package org.prgrms.devcourse.readcircle.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.value.Role;

import java.util.HashMap;
import java.util.Map;

@Data
public class UserDTO {
    private String userId;
    private String password;
    private String email;
    private Role role;
    private String nickname;
    private String profileImageUrl;


    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
    }

    public UserDTO(User user, String uploadPath) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.nickname = user.getNickname();
        this.profileImageUrl = uploadPath+ "/" + user.getProfileImageUrl();
    }

    @JsonIgnore
    public Map<String, Object> getPayload() {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("userId", userId);
        payloadMap.put("email", email);
        payloadMap.put("role", role);
        return payloadMap;
    }
}
