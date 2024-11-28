package org.prgrms.devcourse.readcircle.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.common.value.Address;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.DeliveryStatus;

@Entity
@Getter
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_id", nullable = false)
    private Long id;

    private String recipientName;

    @Embedded
    private Address recipientAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

}
