package org.prgrms.devcourse.readcircle.domain.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "cart")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(User user){
        this.user = user;
    }
    public void changeCartItems(List<CartItem> cartItems){ this.cartItems = cartItems; }

}
