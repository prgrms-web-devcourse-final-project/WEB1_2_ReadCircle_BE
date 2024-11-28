package org.prgrms.devcourse.readcircle.domain.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.TradeType;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="post")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String content;
    private String bookImage;
    private String bookAPIImage;
    private boolean tradeStatus;
    private String isbn;
    private String author;
    private String publisher;
    private String publishDate;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private int price;

    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;

    @Enumerated(EnumType.STRING)
    private BookCondition bookCondition;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @CreatedDate
    private LocalDateTime postCreatedAt;

    @LastModifiedDate
    private LocalDateTime postUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public void changePostTitle(String title){ this.title = title; }
    public void changePostContent(String content){ this.content = content; }
    public void changePostPrice(int price){ this.price = price; }
    public void changeTradeStatus(boolean tradeStatus){ this.tradeStatus = tradeStatus; }
    public void setUser(User user){ this.user = user; }
}
