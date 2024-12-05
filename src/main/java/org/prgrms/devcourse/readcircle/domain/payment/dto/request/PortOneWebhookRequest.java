package org.prgrms.devcourse.readcircle.domain.payment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PortOneWebhookRequest {
    @JsonProperty("imp_uid")
    private String impUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
}
