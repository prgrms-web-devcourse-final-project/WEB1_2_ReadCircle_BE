package org.prgrms.devcourse.readcircle.domain.order.entity.enums;

public enum OrderStatus {
    PENDING,           // 주문 생성, 결제 대기 중
    PAID,              // 결제 완료
    PROCESSING,        // 주문 처리 중 (상품 준비 중)
    SHIPPED,           // 배송 중
    DELIVERED,         // 배송 완료
    CANCELED,          // 주문 취소
    FAILED             // 주문 실패
}
