package org.prgrms.devcourse.readcircle.domain.notification.exception;

import org.springframework.http.HttpStatus;

public enum NotificationException {
    NOT_FOUND_USER_EXCEPTION("Not Found", HttpStatus.NOT_FOUND),
    NOT_REGISTERED_NOTIFICATION_EXCEPTION("Not Registered Notification", HttpStatus.BAD_REQUEST),
    NOT_REGISTERED_EXCEPTION("Not Registered", HttpStatus.BAD_REQUEST),
    NOT_REMOVED_EXCEPTION("Not Removed", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    NotificationException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public NotificationTaskException getTaskException(){
        return new NotificationTaskException(this.message, this.status.value());
    }
}
