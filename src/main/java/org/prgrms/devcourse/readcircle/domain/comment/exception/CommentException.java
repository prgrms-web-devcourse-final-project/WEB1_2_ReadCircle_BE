package org.prgrms.devcourse.readcircle.domain.comment.exception;

import org.springframework.http.HttpStatus;

public enum CommentException {
    NOT_FOUND_EXCEPTION("Not Found", HttpStatus.NOT_FOUND),
    NOT_REGISTERED_EXCEPTION("Not Registered", HttpStatus.BAD_REQUEST),
    NOT_REMOVED_EXCEPTION("Not Removed", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    CommentException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public CommentTaskException getTaskException(){
        return new CommentTaskException(this.message, this.status.value());
    }
}
