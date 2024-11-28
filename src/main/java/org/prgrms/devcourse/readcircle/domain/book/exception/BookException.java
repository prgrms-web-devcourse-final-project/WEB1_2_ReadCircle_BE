package org.prgrms.devcourse.readcircle.domain.book.exception;

public enum BookException {
    NOT_FOUND("NOT_FOUND", 404),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", 404);

    private BookTaskException bookTaskException;

    BookException(final String message, final int code) {
        bookTaskException = new BookTaskException(message, code);
    }

    public BookTaskException get() {
        return bookTaskException;
    }
}
