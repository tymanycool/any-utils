package com.tiany.exception;

public class BlankStringException extends RuntimeException{
    public BlankStringException() {
    }

    public BlankStringException(String message) {
        super(message);
    }

    public BlankStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlankStringException(Throwable cause) {
        super(cause);
    }

    public BlankStringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
