package org.prgrms.devcourse.readcircle.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.TradeType;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long postId;

    @NotBlank(message = "게시글 제목은 필수입니다.")
    private String title;

    private String content;

    @NotBlank(message = "가격 입력은 필수입니다.")
    private int price;

    @NotBlank(message = "책 이미지는 필수입니다.")
    private String bookImage;

    @NotBlank(message = "책 카테고리는 필수입니다.")
    private BookCategory bookCategory;

    @NotBlank(message = "책 상태는 필수입니다.")
    private BookCondition bookCondition;

    @NotBlank(message = "거래 유형 설정은 필수입니다.")
    private TradeType tradeType;

    @NotBlank(message = "ISBN 설정은 필수입니다.")
    private String isbn;
    private String bookAPIImage;
    private String author;
    private String publisher;
    private String publishDate;

    @Builder.Default
    private boolean tradeStatus = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull
    private String userId;
    private String nickname;

    public Post toEntity(){
        User user = User.builder().userId(userId).nickname(nickname).build();

        return Post.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .price(price)
                .bookImage(bookImage)
                .bookAPIImage(bookAPIImage)
                .bookCategory(bookCategory)
                .bookCondition(bookCondition)
                .tradeType(tradeType)
                .isbn(isbn)
                .author(author)
                .publisher(publisher)
                .publishDate(publishDate)
                .tradeStatus(tradeStatus)
                .user(user)
                .build();
    }
    
    public PostDTO(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.bookImage = post.getBookImage();
        this.bookAPIImage = post.getBookAPIImage();
        this.bookCategory = post.getBookCategory();
        this.bookCondition = post.getBookCondition();
        this.tradeType = post.getTradeType();
        this.isbn = post.getIsbn();
        this.author = post.getAuthor();
        this.publisher = post.getPublisher();
        this.publishDate = post.getPublishDate();
        this.tradeStatus = post.isTradeStatus();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.userId = post.getUser().getUserId();
        this.nickname = post.getUser().getNickname();
    }
}
