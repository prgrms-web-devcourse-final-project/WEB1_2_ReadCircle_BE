package org.prgrms.devcourse.readcircle.domain.seller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.seller.entity.Seller;

@Getter
@RequiredArgsConstructor
public class SellerDTO {
    private Long sellerId;
    private String userId;
    private Long bookId;
    private String bank;
    private String account;
    private String accountOwner;
    private int depositAmount;

    public SellerDTO(Seller seller) {
        this.sellerId = seller.getSellerId();
        this.userId = seller.getUserId();
        this.bookId = seller.getBook().getId();
        this.bank = seller.getBank();
        this.account = seller.getAccount();
        this.accountOwner = seller.getAccountOwner();
        this.depositAmount = seller.getDepositAmount();
    }
}
