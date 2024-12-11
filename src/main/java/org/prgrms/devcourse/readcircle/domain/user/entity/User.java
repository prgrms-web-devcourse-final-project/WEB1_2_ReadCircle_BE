package org.prgrms.devcourse.readcircle.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.prgrms.devcourse.readcircle.common.value.Address;

import org.prgrms.devcourse.readcircle.domain.order.entity.Order;
import org.prgrms.devcourse.readcircle.domain.user.entity.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"") // or @Table(name = "`user`")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_no", nullable = false)
    private Long id;

    @Column(name = "userId", unique = true)
    private String userId;

    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    public Address address;

    private String profileImageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(final String userId, final String password, final String email, final String nickname, final String address, final Role role, final String profileImageUrl) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.address = new Address(address);
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

    public void changeAddress(String address) {
        this.address = new Address(address);
    }
}
