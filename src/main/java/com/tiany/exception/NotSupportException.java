package com.tiany.exception;
/**
 * 自定义运行时异常类:不支持该操作时抛出异常
 * @author tianyao
 *
 */
public class NotSupportException extends RuntimeException {
	public NotSupportException() {
	}

	public NotSupportException(String message) {
		super(message);
	}

	public NotSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSupportException(Throwable cause) {
		super(cause);
	}

	public NotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
