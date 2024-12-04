package org.prgrms.devcourse.readcircle.domain.user.exception;

public enum UserException {
    NOT_FOUND("NOT_FOUND", 404),
    DUPLICATE_ID("DUPLICATED_USER", 409),
    DUPLICATE_EMAIL("DUPLICATED_EMAIL", 409),
    DUPLICATE_NICKNAME("DUPLICATE_NICKNAME", 409),
    INVALID("INVALID", 400),
    BAD_CREDENTIALS("BAD_CREDENTIALS", 401),
    NOT_MATCHED_PASSWORD("NOT_MATCHED_PASSWORD", 400),
    FORBIDDEN_ACCESS("FORBIDDEN_ACCESS", 403);

    private UserTaskException userTaskException;

    UserException(final String message, final int code) {
        userTaskException = new UserTaskException(message, code);
    }

    public UserTaskException get() {
        return userTaskException;
    }
}
