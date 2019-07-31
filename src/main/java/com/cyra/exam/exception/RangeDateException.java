package com.cyra.exam.exception;

public class RangeDateException extends Exception {

    public RangeDateException() {
        super();
    }

    public RangeDateException(String message) {
        super(message);
    }

    public RangeDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
