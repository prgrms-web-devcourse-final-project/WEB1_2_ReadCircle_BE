package org.prgrms.devcourse.readcircle.domain.book.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookTaskException extends RuntimeException {
    private String message;
    private int code;
}
