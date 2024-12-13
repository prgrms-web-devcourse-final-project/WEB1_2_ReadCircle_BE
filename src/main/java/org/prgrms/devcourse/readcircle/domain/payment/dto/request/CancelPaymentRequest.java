package org.prgrms.devcourse.readcircle.domain.payment.dto.request;

import lombok.Getter;

@Getter
public class CancelPaymentRequest {
    private String merchantUid;
    private String reason;
}
