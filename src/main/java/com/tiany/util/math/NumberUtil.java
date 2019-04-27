package com.tiany.util.math;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Number工具类
 *
 */
public abstract class NumberUtil {
	//------------常量定义-------------//
	/** 零 */
	public static BigDecimal ZERO = BigDecimal.ZERO;
	/** 一百 */
	public static BigDecimal ONE_HUNDRED = new BigDecimal("100");
	
	
	private static NumberFormat nf = new DecimalFormat("0000");
    public static MathContext mc = new MathContext(16);


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

    public static String round(double value, int n) {
        return String.format("%." + n + "f", value);
    }

    /**
     * double 相加
     * 
     * @param dd
     * @return
     */
    public static double doubleAdd(double... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (double n : dd) {
            result = result.add(new BigDecimal("" + n));
        }
        return result.doubleValue();
    }

    /**
     * double 相加 - 四舍五入
     * 
     * @param dd
     * @return
     */
    public static double doubleAddScaled(double... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (double n : dd) {
            result = result.add(new BigDecimal("" + n));
        }

        return scale(result);
    }

    /**
     * double 相减
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleSubtract(double d1, double d2) {
        return new BigDecimal("" + d1).subtract(new BigDecimal("" + d2)).doubleValue();
    }

    /**
     * double 相减 - 四舍五入
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleSubtractScaled(double d1, double d2) {
        return scale(new BigDecimal("" + d1).subtract(new BigDecimal("" + d2)));
    }

    /**
     * 四舍五入 - RoundingMode.HALF_UP
     * 
     * @param decimal
     * @return
     */
    private static double scale(BigDecimal decimal) {
        return decimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 货币形式格式字符串
     * 
     * @param format ####,###0.00
     * @param value
     */
    public static String formatNumberString(String format, String value) {
        BigDecimal bd = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat(format);
        return df.format(bd);
    }

    /**
     * 相除 取位 d1/d2 - 四舍五入
     * 
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleDivide(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        BigDecimal bd3 = bd1.divide(bd2, mc);
        return bd3.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 相乘 取位 d1*d2 - 四舍五入 默认保留2位
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleMultiply(double d1, double d2) {
        return doubleMultiply(d1, d2, 2);
    }

    /**
     * 相乘 取位 d1*d2 - 四舍五入
     * 
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleMultiply(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2, mc).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 相乘 取位 d1*d2 - 四舍五入
     * 
     * @param scale
     * @param dd
     * @return
     */
    public static double doubleMultiplyScale(int scale, double... dd) {
        BigDecimal result = BigDecimal.ONE;
        for (double n : dd) {
            result = result.multiply(new BigDecimal("" + n), mc);
        }
        return result.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 将整数转化为四位数字
     * 
     * @param number
     * @return
     */
    public static String formatFourNumber(Integer number) {
        return nf.format(number);
    }

    /**
     * 获取带千分符并保留两位小数的金额
     * 
     * @param amount
     * @return
     */
    public static String formatNumber(double amount) {
        String result = NumberFormat.getCurrencyInstance().format(amount).substring(1);
        return result;
    }
	
}
