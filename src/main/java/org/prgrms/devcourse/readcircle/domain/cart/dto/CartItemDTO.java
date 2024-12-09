package org.prgrms.devcourse.readcircle.domain.cart.dto;

import lombok.Data;
import org.prgrms.devcourse.readcircle.domain.cart.entity.CartItem;

@Data
public class CartItemDTO {
    private Long cartId;
    private Long bookId;
    private Long cartItemId;

    private String title;
    private String author;
    private String publisher;
    private int price;
    private String thumbnailUrl;

    public CartItemDTO(CartItem cartItem){
        this.cartId = cartItem.getCart().getCartId();
        this.bookId = cartItem.getBook().getId();
        this.cartItemId = cartItem.getCartItemId();
        this.title = cartItem.getBook().getTitle();
        this.author = cartItem.getBook().getAuthor();
        this.publisher = cartItem.getBook().getPublisher();
        this.price = cartItem.getBook().getPrice();
        this.thumbnailUrl = cartItem.getBook().getThumbnailUrl();
    }
}
