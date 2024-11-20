package org.prgrms.devcourse.readcircle.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCategory;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCondition;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.TradeType;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long postId;
    private String title;
    private String content;
    private int price;
    private String bookImage;
    private BookCategory bookCategory;
    private BookCondition bookCondition;
    private TradeType tradeType;

    @Builder.Default
    private boolean tradeStatus = false;

    private LocalDateTime postCreatedAt;
    private LocalDateTime postUpdatedAt;

    @NotNull
    private String userId;

    public Post toEntity(){
        User user = User.builder().userId(userId).build();

        return Post.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .price(price)
                .bookImage(bookImage)
                .bookCategory(bookCategory)
                .bookCondition(bookCondition)
                .tradeType(tradeType)
                .tradeStatus(tradeStatus)
                .postCreatedAt(postCreatedAt)
                .postUpdatedAt(postUpdatedAt)
                .user(user)
                .build();
    }
    
    public PostDTO(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.bookImage = post.getBookImage();
        this.bookCategory = post.getBookCategory();
        this.bookCondition = post.getBookCondition();
        this.tradeType = post.getTradeType();
        this.tradeStatus = post.isTradeStatus();
        this.postCreatedAt = post.getPostCreatedAt();
        this.postUpdatedAt = post.getPostUpdatedAt();
        this.userId = post.getUser().getUserId();
    }
}
