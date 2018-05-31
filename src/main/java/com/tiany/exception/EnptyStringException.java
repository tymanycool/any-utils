package com.tiany.exception;

public class EnptyStringException extends RuntimeException{
    public EnptyStringException() {
    }

    public EnptyStringException(String message) {
        super(message);
    }

    public EnptyStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnptyStringException(Throwable cause) {
        super(cause);
    }

    public EnptyStringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
