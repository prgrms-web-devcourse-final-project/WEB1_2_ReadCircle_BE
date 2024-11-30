package org.prgrms.devcourse.readcircle.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.domain.order.entity.Order;
import org.prgrms.devcourse.readcircle.domain.order.entity.OrderItem;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.DeliveryStatus;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.OrderStatus;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private List<OrderItemResponse> orderItems;
    private String UserId;
    private String nickname;
    private OrderStatus orderStatus;
    private String address;
    private DeliveryStatus deliveryStatus;
    private int totalPrice;

    public static OrderResponse from(final Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderItems().stream()
                        .map(OrderItemResponse::from)
                        .collect(Collectors.toList()),
                order.getUser().getUserId(),
                order.getUser().getNickname(),
                order.getOrderStatus(),
                order.getDelivery().getRecipientAddress().getAddress(),
                order.getDelivery().getDeliveryStatus(),
                order.getTotalPrice()
        );
    }


}
