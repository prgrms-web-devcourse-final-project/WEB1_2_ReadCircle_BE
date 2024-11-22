package org.prgrms.devcourse.readcircle.domain.comment.exception;

public class CommentTaskException extends RuntimeException {
    private final int statusCode;

    public CommentTaskException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){ return statusCode; }
}
