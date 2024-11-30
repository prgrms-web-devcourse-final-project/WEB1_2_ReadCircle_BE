package org.prgrms.devcourse.readcircle.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
public class OrderArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_archive_id", nullable = false)
    private Long id;

    private Long userId;  // 연관된 User의 ID만 저장
    private Long deliveryId;  // 연관된 Delivery의 ID만 저장
    private String orderStatus;
    private int totalPrice;
    private LocalDateTime orderDate;
    private LocalDateTime cancelledDate;

    @Builder
    public OrderArchive(Long userId, Long deliveryId, String orderStatus, int totalPrice, LocalDateTime orderDate, LocalDateTime cancelledDate) {
        this.userId = userId;
        this.deliveryId = deliveryId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.cancelledDate = cancelledDate;
    }
}
