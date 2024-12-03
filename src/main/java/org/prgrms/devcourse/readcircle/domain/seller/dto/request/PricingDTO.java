package org.prgrms.devcourse.readcircle.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricingDTO {
    private int depositAmount;
    private int price;
    private BookProcess process;
    private BookCondition bookCondition;
}
