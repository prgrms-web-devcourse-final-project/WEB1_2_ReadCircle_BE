package org.prgrms.devcourse.readcircle.domain.notification.exception;

public class NotificationTaskException extends RuntimeException {
    private final int statusCode;

    public NotificationTaskException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){ return statusCode; }
}
