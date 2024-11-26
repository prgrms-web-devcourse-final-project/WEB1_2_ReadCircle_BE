package org.prgrms.devcourse.readcircle.domain.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricingDTO {
    private int price;
    private BookCondition bookCondition;
    private PurchaseStatus purchaseStatus;
}
