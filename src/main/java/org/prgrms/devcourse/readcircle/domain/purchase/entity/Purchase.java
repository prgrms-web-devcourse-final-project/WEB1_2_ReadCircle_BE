package org.prgrms.devcourse.readcircle.domain.purchase.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "purchase")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String bank;
    private String account;
    private String accountOwner;
    private String isbn;
    private int price;

    @Enumerated(EnumType.STRING)
    private BookCondition bookCondition;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void changeBookCondition(BookCondition bookCondition){
        this.bookCondition = bookCondition;
    }
    public void changePurchaseStatus(PurchaseStatus purchaseStatus){
        this.purchaseStatus = purchaseStatus;
    }
    public void changePrice(int price){
        this.price = price;
    }
    public void setUser(User user){ this.user = user; }
}
