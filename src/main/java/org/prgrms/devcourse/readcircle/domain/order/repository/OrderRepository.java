package org.prgrms.devcourse.readcircle.domain.order.repository;

import org.prgrms.devcourse.readcircle.domain.order.entity.Order;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    Page<Order> findAllByUserId(@Param("userId") String userId, Pageable pageable);

    Optional<Order> findByMerchantUid(String merchantUid);

}
