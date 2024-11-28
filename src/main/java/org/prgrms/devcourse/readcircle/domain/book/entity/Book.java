package org.prgrms.devcourse.readcircle.domain.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCategory;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCondition;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookCategory category;

    private String title;

    private String author;

    private String publisher;

    private String description;

    private String isbn;

    private String publishDate;

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private BookCondition bookCondition;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private int price;

    private boolean isForSale = true;

    @Builder
    public Book(
            final BookCategory category,
            final String title, String author,
            final String publisher, String description,
            final String isbn, String publishDate, String thumbnailUrl,
            final BookCondition bookCondition,
            final int price,
            final int stock) {
        this.category = category;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.thumbnailUrl = thumbnailUrl;
        this.bookCondition = bookCondition;
        this.price = price;
    }

    public void updateBookInfo(
            final BookCategory category,
            final String title,
            final String author,
            final String publisher,
            final String description,
            final String isbn,
            final String publishDate,
            final String thumbnailUrl,
            final BookCondition bookCondition,
            final int price,
            final boolean isForSale
    ) {
        this.category = category;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.thumbnailUrl = thumbnailUrl;
        this.bookCondition = bookCondition;
        this.price = price;
        this.isForSale = isForSale;
    }

}