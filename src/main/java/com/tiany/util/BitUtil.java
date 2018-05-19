package com.tiany.util;

import com.tiany.exception.NotSupportException;

public class BitUtil {
	/**
	 * 返回index位上的二进制数值 :false表示0,true表示1
	 * 
	 * @param index
	 *            位置
	 * @param value
	 *            操作数
	 * @return 返回index位上的二进制数值,index 取值在[1,8]
	 */
	public static boolean getBit(byte value, int index) throws IllegalArgumentException {
		if (index < 1 || index > 8) {
			throw new IllegalArgumentException("index 取值在[1,8]");
		}
		return 1 == (value >> index - 1 & 1);// - 优先级大于 >> 优先级大于 &
	}

	/**
	 * 返回index位上的二进制数值 :false表示0,true表示1
	 * 
	 * @param index
	 *            位置
	 * @param value
	 *            操作数
	 * @return 返回index位上的二进制数值,index 取值在[1,32]
	 */
	public static boolean getBit(int value, int index) throws IllegalArgumentException {
		if (index < 1 || index > 32) {
			throw new IllegalArgumentException("index 取值在[1,32]");
		}
		return 1 == (value >> index - 1 & 1);// - 优先级大于 >> 优先级大于 &
	}

	/**
	 * 返回index位上的二进制数值 :false表示0,true表示1
	 * 
	 * @param index
	 *            位置
	 * @param value
	 *            操作数
	 * @return 返回index位上的二进制数值,index 取值在[1,64]
	 */
	public static boolean getBit(long value, int index) throws IllegalArgumentException {
		if (index < 1 || index > 64) {
			throw new IllegalArgumentException("index 取值在[1,64]");
		}
		return 1 == (value >> index - 1 & 1);// - 优先级大于 >> 优先级大于 &
	}

	/**
	 * 返回index位上的二进制数值 :false表示0,true表示1
	 * 
	 * @param index
	 *            位置
	 * @param value
	 *            操作数
	 * @return 返回index位上的二进制数值,index 取值在[1,16]
	 */
	public static boolean getBit(short value, int index) throws IllegalArgumentException {
		assert index >= 1 && index <= 16 : "index >= 1 并且 index <= 16";
		if (index < 1 || index > 16) {
			throw new IllegalArgumentException("index 取值在[1,16]");
		}
		return 1 == (value >> index - 1 & 1);// - 优先级大于 >> 优先级大于 &
	}

	/**
	 * 得到二进制表示的字符串对应的int(十进制).
	 * 
	 * <pre>
	 * 被StringUtil中的{@link StringUtil #getIntByBinaryString(String)},
	 * {@link StringUtil #getIntByBinary(String)}替代
	 * </pre>
	 * 
	 * @param bitStr
	 *            value对应的原码
	 * @return 二进制表示的字符串对应的int(十进制)
	 * @link StringUtil
	 */
	@Deprecated
	public static int getInt(String bitStr) throws NotSupportException {
		if (bitStr.length() > 31 || bitStr.length() == 0) {
			throw new NotSupportException("bitStr 不能转换");
		}
		int rt = 0;
		for (int i = bitStr.length() - 1, j = 0; i >= 0; i--, j++) {
			switch (bitStr.charAt(i)) {
			case '1':
				rt += 1 << j;
				break;
			case '0':
				break;
			default:
				throw new NotSupportException("bitStr 不能转换");
			}
		}
		return rt;
	}

	/**
	 * 设置value上某一位的值: false表示0,true表示1
	 * 
	 * @param value
	 *            操作数
	 * @param index
	 *            位置,index 取值在[1,8]
	 * @param bool
	 *            true表示1,false表示0
	 * @return 返回结果
	 */
	public static byte setBit(byte value, int index, boolean bool) throws IllegalArgumentException {
		if (index < 1 || index > 8) {
			throw new IllegalArgumentException("index 取值在[1,8]");
		}
		return (byte) (bool ? 1 << (index - 1) | value : ~(1 << index - 1) & value);
	}

	/**
	 * 设置value上某一位的值: false表示0,true表示1
	 * 
	 * @param value
	 *            操作数
	 * @param index
	 *            位置,index 取值在[1,32]
	 * @param bool
	 *            true表示1,false表示0
	 * @return 返回结果
	 */
	public static int setBit(int value, int index, boolean bool) throws IllegalArgumentException {
		if (index < 1 || index > 32) {
			throw new IllegalArgumentException("index 取值在[1,32]");
		}
		return bool ? 1 << (index - 1) | value : ~(1 << index - 1) & value;
	}

	/**
	 * 设置value上某一位的值: false表示0,true表示1
	 * 
	 * @param value
	 *            操作数
	 * @param index
	 *            位置,index 取值在[1,64]
	 * @param bool
	 *            true表示1,false表示0
	 * @return 返回结果
	 */
	public static long setBit(long value, int index, boolean bool) throws IllegalArgumentException {
		if (index < 1 || index > 64) {
			throw new IllegalArgumentException("index 取值在[1,64]");
		}
		return bool ? 1L << (index - 1) | value : ~(1L << index - 1) & value;// L不能少因为会精度损失
	}

	/**
	 * 设置value上某一位的值: false表示0,true表示1
	 * 
	 * @param value
	 *            操作数
	 * @param index
	 *            位置,index 取值在[1,16]
	 * @param bool
	 *            true表示1,false表示0
	 * @return 返回结果
	 */
	public static short setBit(short value, int index, boolean bool) throws IllegalArgumentException {
		if (index < 1 || index > 16) {
			throw new IllegalArgumentException("index 取值在[1,16]");
		}
		return (short) (bool ? 1 << (index - 1) | value : ~(1 << index - 1) & value);
	}

}
