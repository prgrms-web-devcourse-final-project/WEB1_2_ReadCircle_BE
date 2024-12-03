package org.prgrms.devcourse.readcircle.domain.cart.exception;

import org.springframework.http.HttpStatus;

public enum CartException {
    NOT_FOUND_CART_EXCEPTION("Not Found Cart", HttpStatus.NOT_FOUND),
    NOT_FOUND_CART_ITEM_EXCEPTION("Not Found Cart Item", HttpStatus.NOT_FOUND),
    DUPLICATED_BOOK_EXCEPTION("Duplicated book", HttpStatus.BAD_REQUEST),
    NOT_REGISTERED_EXCEPTION("Not Registered", HttpStatus.BAD_REQUEST),
    NOT_REMOVED_EXCEPTION("Not Removed", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    CartException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public CartTaskException getTaskException(){
        return new CartTaskException(this.message, this.status.value());
    }
}
