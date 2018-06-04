package com.tiany.util.math;

import com.tiany.exception.NotSupportException;

/**
 * 数学工具类
 * 
 * @author tianyao
 *
 */
public abstract class MathUtil {
	/**
	 * 比较两个double是否相等:|d1-d2|<=precision
	 * 
	 * @param d1
	 *            数值d1
	 * @param d2
	 *            数值d2
	 * @param precision
	 *            精度,参考值:0.00001
	 * @return 如果|d1-d2|<=precision,返回true,否则false
	 */
	public static boolean equals(double d1, double d2, double precision) {
		if (Math.abs(d1 - d2) <= precision) {
			return true;
		}
		return false;
	}

	/**
	 * 计算表达式的值
	 * 
	 * @param expression
	 *            表示式字符串
	 * @return 返回计算的结果
	 * @throws NotSupportException
	 */
	public static double evaluate(String expression) throws NotSupportException {
		Evaluate eval = null;
		try {
			eval = new Evaluate(expression);
		} catch (NotSupportException e) {
			throw e;
		}
		return eval.get();
	}

	/**
	 * 求double类型最大值
	 * 
	 * @param ds
	 *            参数列表为double类型
	 * @return 返回最大值
	 */
	public static double max(double... ds) {
		assert ds.length >= 1;
		double m = ds[0];
		for (int i = 0; i < ds.length; i++) {
			m = Math.max(m, ds[i]);
		}
		return m;
	}

	/**
	 * 求int类型最大值
	 * 
	 * @param is
	 *            参数列表为int类型
	 * @return 返回最大值
	 */
	public static int max(int... is) {
		assert is.length >= 1;
		int m = is[0];
		for (int i = 0; i < is.length; i++) {
			m = Math.max(m, is[i]);
		}
		return m;
	}

	/**
	 * 求Double类型最大值
	 * 
	 * @param ds
	 *            参数列表为Double类型
	 * @return 返回最大值
	 */
	public static Double maxDouble(Double... ds) {
		assert ds.length >= 1;
		double m = ds[0];
		for (int i = 0; i < ds.length; i++) {
			m = Math.max(m, ds[i]);
		}
		return m;
	}

	/**
	 * 求Integer类型最大值
	 * 
	 * @param is
	 *            参数列表为Integer类型
	 * @return 返回最大值
	 */
	public static Integer maxInteger(Integer... is) {
		assert is.length >= 1;
		int m = is[0];
		for (int i = 0; i < is.length; i++) {
			m = Math.max(m, is[i]);
		}
		return m;
	}

	/**
	 * 求double类型最小值
	 * 
	 * @param ds
	 *            参数列表为double类型
	 * @return 返回最小值
	 */
	public static double min(double... ds) {
		assert ds.length >= 1;
		double m = ds[0];
		for (int i = 0; i < ds.length; i++) {
			m = Math.min(m, ds[i]);
		}
		return m;
	}

	/**
	 * 求int类型最小值
	 * 
	 * @param is
	 *            参数列表为int类型
	 * @return 返回最小值
	 */
	public static int min(int... is) {
		assert is.length >= 1;
		int m = is[0];
		for (int i = 0; i < is.length; i++) {
			m = Math.min(m, is[i]);
		}
		return m;
	}

	/**
	 * 求Double类型最小值
	 * 
	 * @param ds
	 *            参数列表为Double类型
	 * @return 返回最小值
	 */
	public static Double minDouble(Double... ds) {
		assert ds.length >= 1;
		Double m = ds[0];
		for (int i = 0; i < ds.length; i++) {
			m = Math.min(m, ds[i]);
		}
		return m;
	}

	/**
	 * 求Integer类型最小值
	 * 
	 * @param is
	 *            参数列表为Integer类型
	 * @return 返回最小值
	 */
	public static Integer minInteger(Integer... is) {
		assert is.length >= 1;
		Integer m = is[0];
		for (int i = 0; i < is.length; i++) {
			m = Math.min(m, is[i]);
		}
		return m;
	}

	/**
	 * 得到[0,max)区间的随机数,不包括最大值
	 * 
	 * @param max
	 *            随机数的最大值
	 * @return 返回double类型的随机数
	 */
	public static double random(double max) {
		return random(0, max);
	}

	/**
	 * 得到[min,max)区间的随机数,不包括最大值
	 * 
	 * @param min
	 *            随机数的最小值
	 * @param max
	 *            随机数的最大值
	 * @return 返回double类型的随机数
	 */
	public static double random(double min, double max) {
		assert min < max : "最大值必须大于最小值";
		double rt = min + (max - min) * Math.random();
		double precision = 0.00001;// 精度,数值越大出现min的概率越大
		if (equals(rt, min, precision)) {
			return min;
		}
		return rt;
	}

	/**
	 * 得到[0,max]区间的随机数
	 * 
	 * @param max
	 *            随机数的最大值
	 * @return 返回int类型的随机数
	 */
	public static int random(int max) {
		return random(0, max);
	}

	/**
	 * 得到[min,max]区间的随机数
	 * 
	 * @param min
	 *            随机数的最小值
	 * @param max
	 *            随机数的最大值
	 * @return 返回int类型的随机数
	 */
	public static int random(int min, int max) {
		assert min < max : "最大值必须大于最小值";
		return min + (int) ((max - min + 1) * Math.random());
	}

	/**
	 * 得到[0,max]区间的随机数,包括最大值
	 * 
	 * @param max
	 *            随机数的最大值
	 * @return 返回double类型的随机数
	 */
	public static double randomContainsMax(double max) {
		return randomContainsMax(0, max);
	}

	/**
	 * 得到[min,max]区间的随机数,包括最大值
	 * 
	 * @param min
	 *            随机数的最小值
	 * @param max
	 *            随机数的最大值
	 * @return 返回double类型的随机数
	 */
	public static double randomContainsMax(double min, double max) {
		assert min < max : "最大值必须大于最小值";
		double rt = min + (max - min) * Math.random();
		double precision = 0.00001;// 精度,数值越大出现min,max的概率越大
		if (equals(rt, min, precision)) {
			return min;
		}
		if (equals(rt, max, precision)) {
			return max;
		} else {
			return rt;
		}
	}

	/**
	 * value保留n位小数.<br>
	 * round(3.032,2)==3.03;<br>
	 * round(3.36,0)==3;<br>
	 * round(3.035,2)==3.04;
	 * 
	 * @param value
	 *            进行运算的数
	 * @param n
	 *            >=0;为小数点后面的位数
	 * @return 返回value保留n位小数的结果
	 */
	public static double round(double value, int n) {
		assert n >= 0 : "error:public static double round(double value, int n).\nthe argument n must >=0";
		int a = 1;
		for (int i = 0; i < n; i++) {
			a = a * 10;
		}
		if (value >= 0) {
			value = (double) (int) (value * a + 0.5) / a;
		} else {
			value = (double) (int) (value * a - 0.5) / a;
		}
		return value;
	}

	/**
	 * 私有化构造,只能通过构造调用方法
	 */
	private MathUtil() {
		throw new AssertionError();
	}
}
