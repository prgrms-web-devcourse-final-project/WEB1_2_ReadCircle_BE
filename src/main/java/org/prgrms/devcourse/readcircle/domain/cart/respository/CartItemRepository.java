package org.prgrms.devcourse.readcircle.domain.cart.respository;

import org.prgrms.devcourse.readcircle.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
