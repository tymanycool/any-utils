package com.tiany.util.random;

import java.util.UUID;

/**
 * UUID工具
 * @author tWX508286
 *
 */
public abstract class UUIDUtil {
	/**
	 * 获取UUID，去掉其中的'-'
	 * @return 去掉其中的'-'的UUID
	 */
	public static String getUUIDString(){
		String result =UUID.randomUUID().toString().replaceAll("-", "");
		return result;
	}
	
}
