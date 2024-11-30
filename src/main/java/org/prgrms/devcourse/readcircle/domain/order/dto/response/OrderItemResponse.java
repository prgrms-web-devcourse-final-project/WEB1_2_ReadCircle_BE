package org.prgrms.devcourse.readcircle.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.domain.order.entity.OrderItem;

@Getter
@AllArgsConstructor
public class OrderItemResponse {
    private Long bookId;  // 상품 ID
    private String bookTitle;  // 상품명
    private String author;
    private String publisher;
    private String isbn;
    private String publishDate;
    private String thumbnailUrl;
    private BookCondition bookCondition;

    private int price;  // 상품 가격

    public static OrderItemResponse from(final OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getBook().getTitle(),
                orderItem.getBook().getAuthor(),
                orderItem.getBook().getPublisher(),
                orderItem.getBook().getIsbn(),
                orderItem.getBook().getPublishDate(),
                orderItem.getBook().getThumbnailUrl(),
                orderItem.getBook().getBookCondition(),
                orderItem.getPrice()
        );
    }
}
