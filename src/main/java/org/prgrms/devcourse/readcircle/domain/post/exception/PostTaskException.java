package org.prgrms.devcourse.readcircle.domain.post.exception;

public class PostTaskException extends RuntimeException {
    private final int statusCode;

    public PostTaskException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){ return statusCode; }
}
