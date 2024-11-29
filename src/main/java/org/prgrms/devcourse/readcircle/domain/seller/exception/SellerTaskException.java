package org.prgrms.devcourse.readcircle.domain.seller.exception;

public class SellerTaskException extends RuntimeException {
    private final int statusCode;

    public SellerTaskException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){ return statusCode; }
}
