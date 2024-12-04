package org.prgrms.devcourse.readcircle.domain.cart.respository;

import org.prgrms.devcourse.readcircle.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(" SELECT c FROM Cart c WHERE c.user.userId = :userId ")
    Optional<Cart> findByUserId(String userId);

    @Query(" SELECT c FROM Cart c JOIN FETCH c.cartItems WHERE c.user.userId = :userId ")
    Optional<Cart> findByUserIdWithCartItems(String userId);
}
