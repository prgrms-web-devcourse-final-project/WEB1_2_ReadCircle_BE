package org.prgrms.devcourse.readcircle.domain.book.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;

@Getter
public class BookUpdateRequest {

    @NotBlank(message = "책 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "저자는 필수입니다.")
    private String author;

    @NotBlank(message = "출판사는 필수입니다.")
    private String publisher;

    @NotBlank(message = "책 설명는 필수입니다.")
    private String description;

    @NotBlank(message = "isbn은 필수입니다.")
    private String isbn;

    @NotBlank(message = "출판 날짜는 필수입니다.")
    private String publishDate;

    @NotBlank(message = "책 이미지는 필수입니다.")
    private String thumbnailUrl;

    @NotNull(message = "책 상태는 필수입니다.")
    private BookCondition bookCondition;

    @NotNull(message = "책 카테고리는 필수입니다.")
    private BookCategory category;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private int price;

    private boolean isForSale;

    private BookProcess process;

    public BookUpdateRequest(String title, BookCategory bookCategory, String isbn, String author, String publisher, String publishDate, String description, String thumbnailUrl, BookCondition bookCondition, int price, BookProcess process, boolean isForSale) {
        this.title = title;
        this.category = bookCategory;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.bookCondition = bookCondition;
        this.price = price;
        this.process = process;
        this.isForSale = isForSale;
    }
}
