package org.prgrms.devcourse.readcircle.domain.user.repository;

import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserFindRepository extends JpaRepository<User, Long> {
    @Query(" SELECT u FROM User u WHERE u.userId = :userId ")
    Optional<User> findByUserId(String userId);
}
