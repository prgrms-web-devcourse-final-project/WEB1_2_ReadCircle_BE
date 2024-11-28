package org.prgrms.devcourse.readcircle.domain.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.Purchase;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDTO {
    private Long purchaseId;
    private String userId;
    private String bank;
    private String account;
    private String accountOwner;
    private String isbn;
    private BookCondition bookCondition;
    private int price;
    private PurchaseStatus purchaseStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Purchase toEntity(){
        return Purchase.builder()
                .purchaseId(purchaseId)
                .bank(bank)
                .account(account)
                .accountOwner(accountOwner)
                .isbn(isbn)
                .bookCondition(bookCondition)
                .price(price)
                .purchaseStatus(purchaseStatus)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .user(User.builder().userId(userId).build())
                .build();
    }

    public PurchaseDTO(Purchase purchase){
        this.purchaseId = purchase.getPurchaseId();
        this.bank = purchase.getBank();
        this.account = purchase.getAccount();
        this.accountOwner = purchase.getAccountOwner();
        this.isbn = purchase.getIsbn();
        this.bookCondition = purchase.getBookCondition();
        this.price = purchase.getPrice();
        this.purchaseStatus = purchase.getPurchaseStatus();
        this.createdAt = purchase.getCreatedAt();
        this.updatedAt = purchase.getUpdatedAt();
        this.userId = purchase.getUser().getUserId();
    }
}
