package com.tiany.util.random;

public abstract class RandomUtil {

	/**
	 * 在【min,max】中随机生成一位数字
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumber(int min, int max) {
		// (最小值+Math.random()*(最大值-最小值+1))
		return (int) (min + Math.random() * (max - min + 1));
	}

	/**
	 * 得到num位的随机字符串
	 * 
	 * @param num
	 * @return
	 */
	public static String getRandomString(int num) {
		String randomString = "";
		int n = num / 32;
		int m = num % 32;
		for (int i = 0; i < n; i++) {
			randomString += UUIDUtil.getUUIDString();
		}
		if (m != 0) {
			randomString += UUIDUtil.getUUIDString();
		}

		return randomString.substring(0, num);
	}

	/**
	 * 随机返回数组中的一个字符
	 * 
	 * @param arr
	 * @return
	 */
	public static char getRandomChar(char[] arr) {
		int randomNumber = getRandomNumber(0, arr.length - 1);
		return arr[randomNumber];
	}

	/**
	 * 随机返回数组中的一个数
	 * 
	 * @param arr
	 * @return
	 */
	public static int getRandomInt(int[] arr) {
		int randomNumber = getRandomNumber(0, arr.length - 1);
		return arr[randomNumber];
	}

	/**
	 * 随机返回数组中的一个字符串
	 * 
	 * @param arr
	 * @return
	 */
	public static String getRandomString(String[] arr) {
		int randomNumber = getRandomNumber(0, arr.length - 1);
		return arr[randomNumber];
	}

	/**
	 * 得到num位的随机数字
	 * 
	 * @param num
	 * @return
	 */
	public static String getRandomNumber(int num) {
		String randomString = "";
		for (int i = 0; i < num; i++) {
			randomString += getRandomNumber(0, 9);
		}

		return randomString.substring(0, num);
	}

}
