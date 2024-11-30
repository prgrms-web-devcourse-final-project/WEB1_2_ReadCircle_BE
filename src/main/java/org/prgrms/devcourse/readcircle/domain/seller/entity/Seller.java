package org.prgrms.devcourse.readcircle.domain.seller.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="seller")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Seller extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @NotBlank
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotBlank
    private String bank;

    @NotBlank
    private String account;

    @NotBlank
    private String accountOwner;

    private int depositAmount;

    public Seller(Book book, String userId, String bank, String account, String accountOwner) {
        this.userId = userId;
        this.book = book;
        this.bank = bank;
        this.account = account;
        this.accountOwner = accountOwner;
    }

    public void changeDepositAmount(int depositAmount){ this.depositAmount = depositAmount; }

}
