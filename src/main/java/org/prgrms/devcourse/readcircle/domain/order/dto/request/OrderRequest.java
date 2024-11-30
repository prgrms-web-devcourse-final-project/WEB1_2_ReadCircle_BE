package org.prgrms.devcourse.readcircle.domain.order.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private List<Long> bookList;  // 주문할 상품 ID

    private String address;  // 배송지 주소

    private String recipientName;
}
