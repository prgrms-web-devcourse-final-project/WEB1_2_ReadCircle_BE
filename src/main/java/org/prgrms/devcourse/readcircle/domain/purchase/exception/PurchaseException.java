package org.prgrms.devcourse.readcircle.domain.purchase.exception;

import org.springframework.http.HttpStatus;

public enum PurchaseException {
    NOT_FOUND_EXCEPTION("Not Found", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER_EXCEPTION("Not Found User", HttpStatus.NOT_FOUND),
    NOT_REGISTERED_EXCEPTION("Not Registered", HttpStatus.BAD_REQUEST),
    NOT_MODIFIED_EXCEPTION("Not Modified", HttpStatus.BAD_REQUEST),
    NOT_REMOVED_EXCEPTION("Not Removed", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    PurchaseException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public PurchaseTaskException getTaskException(){
        return new PurchaseTaskException(this.message, this.status.value());
    }
}
