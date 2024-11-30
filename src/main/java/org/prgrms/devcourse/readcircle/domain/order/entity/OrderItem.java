package org.prgrms.devcourse.readcircle.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int price;

    @Builder
    public OrderItem(Book book, int price) {
        this.book = book;
        this.price = price;

        // orderItem 이 생성되면 상품의 판매상태를 변경
        book.changeIsForSale(false);
    }

    public void changeOrder(Order order) {
        this.order = order;
    }




}
