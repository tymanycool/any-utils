package com.tiany.exception;
/**
 * 自定义运行时异常类:不支持该操作时抛出异常
 * @author tianyao
 *
 */
public class NotSupportException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 使用null作为不支持异常的初始信息
	 */
	public NotSupportException(){
		super();
	}
	/**
	 * 构造不支持该操作的异常：NotSupportException
	 * @param info 异常的详细信息
	 */
	public NotSupportException(String info){
		super(info);
	}
	/**
	 * 构造不支持该操作的异常：NotSupportException
	 * @param info 异常的详细信息
	 * @param cause 产生异常的原因
	 */
	public NotSupportException(String info, String cause){
		super(info,new Throwable(cause));
	}
}
