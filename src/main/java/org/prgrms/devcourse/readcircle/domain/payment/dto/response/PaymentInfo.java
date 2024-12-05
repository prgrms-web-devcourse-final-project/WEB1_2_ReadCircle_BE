package org.prgrms.devcourse.readcircle.domain.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentInfo {
    @JsonProperty("status")
    private String status;
    @JsonProperty("amount")
    private int amount;
}
