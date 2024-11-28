package org.prgrms.devcourse.readcircle.domain.post.exception;

import org.springframework.http.HttpStatus;

public enum PostException {
    NOT_FOUND_EXCEPTION("Not Found", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER("Not Found User", HttpStatus.NOT_FOUND),
    NOT_REGISTERED_EXCEPTION("Not Registered", HttpStatus.BAD_REQUEST),
    NOT_REGISTERED_JSON_EXCEPTION("Invalid Json format", HttpStatus.BAD_REQUEST),
    NOT_MODIFIED_EXCEPTION("Not Modified", HttpStatus.BAD_REQUEST),
    NOT_REMOVED_EXCEPTION("Not Removed", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    PostException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    public PostTaskException getTaskException(){
        return new PostTaskException(this.message, this.status.value());
    }
}
