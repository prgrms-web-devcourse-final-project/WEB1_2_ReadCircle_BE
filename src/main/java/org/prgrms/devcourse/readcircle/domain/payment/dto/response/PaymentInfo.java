package org.prgrms.devcourse.readcircle.domain.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class PaymentInfo {
    private String merchantUid; // 주문 고유 ID
    private String impUid; // 포트원의 결제 고유 ID
    private String buyerName; // 구매자 이름
    private String productName; // 상품 이름
    private String paymentMethod;
    private String paymentStatus; // 결제 상태 (e.g., paid, failed)
    private Integer amount; // 결제 금액
    private String currency;
    private String failReason; // 결제 실패 사유 (optional)
}
