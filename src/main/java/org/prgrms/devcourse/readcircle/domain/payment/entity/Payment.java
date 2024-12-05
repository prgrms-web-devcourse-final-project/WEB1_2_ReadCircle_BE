package org.prgrms.devcourse.readcircle.domain.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.prgrms.devcourse.readcircle.domain.order.entity.Order;
import org.prgrms.devcourse.readcircle.domain.payment.entity.enums.PaymentStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String userId;

   /* @Column(nullable = true) // 초기에는 null, PG사 결제 성공 시 업데이트
    private String transactionId;*/

//    private String paymentUid;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private int amount;

//    private String currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PENDING, SUCCESS, FAILED, REFUNDED


    @Builder
    public Payment(Order order, String userId, String transactionId, String paymentMethod, int amount,
                   String currency, PaymentStatus status) {
        this.order = setOrder(order);
        this.userId = userId;
//        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
//        this.currency = currency;
        this.paymentStatus = status;
    }

    private Order setOrder(Order order) {
        this.order = order;
        order.changePayment(this);
        return order;
    }

    public void changeStatus(PaymentStatus status) {
        this.paymentStatus = status;
    }

}
