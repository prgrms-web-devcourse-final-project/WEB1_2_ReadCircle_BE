package org.prgrms.devcourse.readcircle.domain.order.entity.enums;

public enum DeliveryStatus {
    PENDING,           // 배송 대기 (요청 접수)
    PREPARING,         // 배송 준비 중
    SHIPPED,           // 배송 시작 (배송사에 전달됨)
    IN_TRANSIT,        // 배송 중 (물류 이동)
    OUT_FOR_DELIVERY,  // 배달 중 (배송지로 이동)
    DELIVERED,         // 배송 완료
    FAILED,            // 배송 실패 (주소 오류 등)
    RETURN_REQUESTED,  // 반품 요청
    RETURNED,          // 반품 완료
    CANCELED           // 배송 취소
}
