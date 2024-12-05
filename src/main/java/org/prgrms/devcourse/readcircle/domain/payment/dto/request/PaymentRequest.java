package org.prgrms.devcourse.readcircle.domain.payment.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PaymentRequest {
    private String paymentMethod;
    private int amount;
    private List<Long> bookList;  // 주문할 상품 ID

    private String address;  // 배송지 주소

    private String recipientName;

}
