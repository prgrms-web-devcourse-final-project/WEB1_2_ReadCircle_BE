package org.prgrms.devcourse.readcircle.domain.user.repository;


import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<User> findByUserId(String userId);
}
