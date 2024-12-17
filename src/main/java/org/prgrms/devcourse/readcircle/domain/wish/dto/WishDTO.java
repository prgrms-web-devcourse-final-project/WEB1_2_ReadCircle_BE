package org.prgrms.devcourse.readcircle.domain.wish.dto;

import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.wish.entity.Wish;

import java.time.LocalDateTime;


public class WishDTO {
    private Long wishId;
    private User user;
    private Post post;
    private Book book;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WishDTO(Wish wish){
        this.wishId = wish.getWishId();
        this.user = wish.getUser();
        this.post = wish.getPost();
        this.book = wish.getBook();
        this.createdAt = wish.getCreatedAt();
        this.updatedAt = wish.getUpdatedAt();
    }
}
