package org.prgrms.devcourse.readcircle.domain.purchase.exception;

public class PurchaseTaskException extends RuntimeException {
    private final int statusCode;

    public PurchaseTaskException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){ return statusCode; }
}
