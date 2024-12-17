package org.prgrms.devcourse.readcircle.domain.wish.exception;

import org.springframework.http.HttpStatus;

public enum WishException {
    NOT_FOUND_POST_EXCEPTION("Not Found Post", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER_EXCEPTION("Not Found User", HttpStatus.NOT_FOUND),
    DUPLICATED_WISH_EXCEPTION("Duplicated wish", HttpStatus.BAD_REQUEST),
    NOT_REGISTERED_WISH_EXCEPTION("Not Registered Wish", HttpStatus.BAD_REQUEST),
    NOT_MODIFIED_WISH_EXCEPTION("Not Modified Wish", HttpStatus.BAD_REQUEST),
    NOT_REMOVED_WISH_EXCEPTION("Not Removed Wish", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    WishException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public WishTaskException getTaskException(){
        return new WishTaskException(this.message, this.status.value());
    }
}
