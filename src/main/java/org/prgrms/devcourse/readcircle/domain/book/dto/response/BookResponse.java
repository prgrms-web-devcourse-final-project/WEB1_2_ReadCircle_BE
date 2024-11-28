package org.prgrms.devcourse.readcircle.domain.book.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BookResponse {
    private final Long id;
    private final BookCategory category;
    private final String title;
    private final String author;
    private final String publisher;
    private final String description;
    private final String isbn;
    private final String publishDate;
    private final String thumbnailUrl;
    private final BookCondition bookCondition;
    private final int price;
    private final boolean isForSale;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static BookResponse from(final Book book) {
        return new BookResponse(
                book.getId(),
                book.getCategory(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getDescription(),
                book.getIsbn(),
                book.getPublishDate(),
                book.getThumbnailUrl(),
                book.getBookCondition(),
                book.getPrice(),
                book.isForSale(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
