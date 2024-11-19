package org.prgrms.devcourse.readcircle.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTaskException extends RuntimeException {
    private String message;
    private int code;
}
