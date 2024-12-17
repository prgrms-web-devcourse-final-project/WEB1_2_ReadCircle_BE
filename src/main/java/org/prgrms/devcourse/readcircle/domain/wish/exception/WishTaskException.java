package org.prgrms.devcourse.readcircle.domain.wish.exception;

public class WishTaskException extends RuntimeException {
    private final int statusCode;

    public WishTaskException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){ return statusCode; }
}
