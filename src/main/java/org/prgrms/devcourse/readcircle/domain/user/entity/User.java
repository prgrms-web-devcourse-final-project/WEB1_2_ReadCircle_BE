package org.prgrms.devcourse.readcircle.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.prgrms.devcourse.readcircle.domain.user.value.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", unique = true)
    private String userId;

    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "nickname", nullable = false, length = 30)
//    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
//    @Size(min = 2, max = 10, message = "닉네임은 2~10자이어야 합니다.")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String profileImageUrl;

    @Builder
    public User(final String userId, final String password, final String email, final String nickname, final Role role, final String profileImageUrl) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.profileImageUrl = profileImageUrl;
    }

    public void changeProfileImageUrl(final String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


    public void changeNickname(final String nickname) {
        this.nickname = nickname;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void changePassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);  // 비밀번호 암호화 후 변경
    }

    public void changeEmail(String email) {
        this.email = email;
    }
}
