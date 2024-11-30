package org.prgrms.devcourse.readcircle.domain.seller.exception;

import org.springframework.http.HttpStatus;

public enum SellerException {
    NOT_FOUND_EXCEPTION("Not Found", HttpStatus.NOT_FOUND),
    NOT_FOUND_SELLER_EXCEPTION("Not Found Seller", HttpStatus.NOT_FOUND),
    NOT_REGISTERED_SELLER_EXCEPTION("Not Registered SELLER", HttpStatus.BAD_REQUEST),
    NOT_REGISTERED_BOOK_EXCEPTION("Not Registered BOOK", HttpStatus.BAD_REQUEST),
    NOT_MODIFIED_EXCEPTION("Not Modified", HttpStatus.BAD_REQUEST),
    NOT_REMOVED_EXCEPTION("Not Removed", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    SellerException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public SellerTaskException getTaskException(){
        return new SellerTaskException(this.message, this.status.value());
    }
}
