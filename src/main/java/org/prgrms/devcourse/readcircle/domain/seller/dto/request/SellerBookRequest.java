package org.prgrms.devcourse.readcircle.domain.seller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;

@Getter
@Builder
public class SellerBookRequest {

    @NotBlank(message = "은행 이름 입력은 필수입니다.")
    private String bank;

    @NotBlank(message = "계좌번호 입력은 필수입니다.")
    private String account;

    @NotBlank(message = "계좌 소유자 이름 입력은 필수입니다.")
    private String accountOwner;


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
    private BookCategory bookCategory;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private int price;

    private BookProcess process;
}
