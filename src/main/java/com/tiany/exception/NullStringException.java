package com.tiany.exception;


public class NullStringException extends RuntimeException{
    public NullStringException() {
    }

    public NullStringException(String message) {
        super(message);
    }

    public NullStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullStringException(Throwable cause) {
        super(cause);
    }

    public NullStringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
