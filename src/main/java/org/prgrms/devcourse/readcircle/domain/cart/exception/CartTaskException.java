package org.prgrms.devcourse.readcircle.domain.cart.exception;

public class CartTaskException extends RuntimeException {
    private final int statusCode;

    public CartTaskException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){ return statusCode; }
}
