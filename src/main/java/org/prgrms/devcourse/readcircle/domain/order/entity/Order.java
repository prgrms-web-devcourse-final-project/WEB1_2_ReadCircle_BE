package org.prgrms.devcourse.readcircle.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.OrderStatus;
import org.prgrms.devcourse.readcircle.domain.payment.entity.Payment;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String merchantUid; // 포트원 결제 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    @CreatedDate
    private LocalDateTime orderDate;

    @Builder
    public Order(Delivery delivery, OrderStatus orderStatus, User user, int totalPrice, List<OrderItem> orderItems, String merchantUid) {
        this.delivery = setDelivery(delivery);
        this.user = setUser(user);
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
        this.merchantUid = merchantUid;
    }

    public User setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
        return user;
    }

    public Delivery setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.changeOrder(this);
        return delivery;
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItem != null) {
            orderItems.add(orderItem);  // 컬렉션에 추가
            orderItem.changeOrder(this);  // OrderItem의 'order' 필드를 설정
        }
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public void changeTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public void changePayment(Payment payment) { this.payment = payment; }
}
