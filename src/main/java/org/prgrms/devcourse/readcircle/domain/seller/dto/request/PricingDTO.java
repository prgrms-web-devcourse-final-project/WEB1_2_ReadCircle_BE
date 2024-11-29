package org.prgrms.devcourse.readcircle.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricingDTO {
    private Long bookId;
    private int depositAmount;
    private int price;
    private BookProcess process;
    private BookCondition bookCondition;
}
