package org.prgrms.devcourse.readcircle.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address recipientAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public void changeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public void changeRecipientAddress(Address address) { this.recipientAddress = address; }
    public void changeRecipientName(String recipientName) { this.recipientName = recipientName; }

    public void changeOrder(Order order) {
        this.order = order;
    }
}
