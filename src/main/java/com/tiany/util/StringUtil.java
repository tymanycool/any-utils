package com.tiany.util;

import com.tiany.inf.Condition;
import com.tiany.util.not.Not;
import org.apache.commons.lang3.StringUtils;

import java.util.StringTokenizer;

/**
 * 字符串工具类
 *
 * @author tianyao
 */
public abstract class StringUtil extends StringUtils {

    /**
     * 判断一个字符串是否为空
     * 1.如果对象str==null 则为空
     * 2.如果字符串长度 小于1 ，str.length()<1 则为空
     * 3.如果字符串 为 "null" ,则为空
     * 4.如果字符串为"  ",含有空格，则为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str || str.length() <= 0 || "null".equals(str) || str.trim().length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断一个字符串是否为空
     * 1.如果对象str!=null 则不为空
     * 2.如果字符串长度 大于0 ，str.length()>0 则不为空
     * 3.如果字符串 为 "null" ,
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (null != str && str.length() > 0 && !"null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断一个字符串的编码格式
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String getEncoding(String str) {
        if (StringUtils.isEmpty(str)) return str;
        String[] encodeArr = new String[]{"ISO-8859-1", "GB2312", "GBK", "UTF-8", "UTF-16"};
        try {
            for (String encode : encodeArr) {
                if (str.equals(new String(str.getBytes(encode), encode))) {
                    return encode;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 此方法将给出的字符串source使用delim划分为单词数组。
     *
     * @param source 需要进行划分的原字符串
     * @param delim  单词的分隔字符串
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组，
     * 如果delim为null则使用逗号作为分隔字符串。
     * @since 0.1
     */
    public static String[] split(String source, String delim) {
        String[] wordLists;
        if (source == null) {
            wordLists = new String[1];
            wordLists[0] = source;
            return wordLists;
        }
        if (delim == null) {
            delim = ",";
        }
        StringTokenizer st = new StringTokenizer(source, delim);
        int total = st.countTokens();
        wordLists = new String[total];
        for (int i = 0; i < total; i++) {
            wordLists[i] = st.nextToken();
        }
        return wordLists;
    }

    /**
     * 比较字符串相等，如果有一个相等则返回ture
     *
     * @param dest
     * @param arr
     * @return
     */
    public static boolean eq_or(String dest, String... arr) {
        for (String s : arr) {
            if (s.equals(dest))
                return true;
        }
        return false;
    }

    /**
     * 设置String的index上的值
     *
     * @param str
     * @param newValue
     * @param index
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    @Deprecated
    public static String setCharAtBySub(String str, char newValue, int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= str.length()) {
            throw new ArrayIndexOutOfBoundsException("index超出str的范围...");
        }
        return str.substring(0, index) + newValue + str.substring(index + 1);
    }

    /**
     * 设置String的index上的值
     *
     * @param str
     * @param newValue
     * @param index
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    public static String setCharAt(String str, char newValue, int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= str.length()) {
            throw new ArrayIndexOutOfBoundsException("index超出str的范围...");
        }
        StringBuffer sb = new StringBuffer(str);
        sb.setCharAt(index, newValue);
        return sb.toString();
    }

    /**
     * 在String的index上插入新的值
     *
     * @param str
     * @param newValue
     * @param index
     * @return 返回插入后的字符串
     * @throws ArrayIndexOutOfBoundsException
     */
    public static String insert(String str, char newValue, int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= str.length()) {
            throw new ArrayIndexOutOfBoundsException("index超出str的范围...");
        }
        StringBuffer sb = new StringBuffer(str);
        sb.insert(index, newValue);
        return sb.toString();
    }

    /**
     * 计数
     * <p>
     * <pre>
     *  public static int getSubStrCount(String str, String subStr, int startPos)
     * </pre>
     */
    private static int countOfString;

    /**
     * 十进制转换二进制
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinary(byte value) {
        String str = "";
        boolean negative = false;
        if (value < 0) {
            value = (byte) -value;
            negative = true; // 是负数
        }
        while (value != 0) {
            str += value % 2;
            value /= 2;
        }
        while (str.length() < 8) {// byte 8位
            str += '0';
        }
        str = reverse(str);// 字符串反向
        if (negative) {// 是负数时执行后面的取反加1操作
            StringBuffer sb = new StringBuffer(str);
            reverseThenAddOne(sb);
            return sb.toString();
        }
        return str;
    }

    /**
     * 十进制转换二进制
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinary(int value) {
        String str = "";
        boolean negative = false;
        if (value < 0) {
            value = -value;
            negative = true; // 是负数
        }
        while (value != 0) {
            str += value % 2;
            value /= 2;
        }
        while (str.length() < 32) {// int 32位
            str += '0';
        }
        str = reverse(str);// 字符串反向
        if (negative) {// 是负数时执行后面的取反加1操作
            StringBuffer sb = new StringBuffer(str);
            reverseThenAddOne(sb);
            return sb.toString();
        }
        return str;
    }

    /**
     * 十进制转换二进制
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinary(long value) {
        String str = "";
        boolean negative = false;
        if (value < 0) {
            value = -value;
            negative = true; // 是负数
        }
        while (value != 0) {
            str += value % 2;
            value /= 2;
        }
        while (str.length() < 64) {// long 64位
            str += '0';
        }
        str = reverse(str);// 字符串反向
        if (negative) {// 是负数时执行后面的取反加1操作
            StringBuffer sb = new StringBuffer(str);
            reverseThenAddOne(sb);
            return sb.toString();
        }
        return str;
    }

    /**
     * 十进制转换二进制
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinary(short value) {
        String str = "";
        boolean negative = false;
        if (value < 0) {
            value = (short) -value;
            negative = true; // 是负数
        }
        while (value != 0) {
            str += value % 2;
            value /= 2;
        }
        while (str.length() < 16) {// short 16位
            str += '0';
        }
        str = reverse(str);// 字符串反向
        if (negative) {// 是负数时执行后面的取反加1操作
            StringBuffer sb = new StringBuffer(str);
            reverseThenAddOne(sb);
            return sb.toString();
        }
        return str;
    }

    /**
     * 十进制转换二进制,与getBinary具有相同的功能,只是算法更好
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinaryString(byte value) {
        String str = "";
        for (int i = 8; i >= 1; i--) {
            str += (BitUtil.getBit(value, i) ? '1' : "0");
        }
        return str;
    }

    /**
     * 十进制转换二进制,与getBinary具有相同的功能,只是算法更好
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinaryString(int value) {
        String str = "";
        for (int i = 32; i >= 1; i--) {
            str += (BitUtil.getBit(value, i) ? '1' : "0");
        }
        return str;
    }

    /**
     * 十进制转换二进制,与getBinary具有相同的功能,只是算法更好
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinaryString(long value) {
        String str = "";
        for (int i = 64; i >= 1; i--) {
            str += (BitUtil.getBit(value, i) ? '1' : "0");
        }
        return str;
    }

    /**
     * 十进制转换二进制,与getBinary具有相同的功能,只是算法更好
     *
     * @param value 要转换的数值(10进制)
     * @return 返回二进制表示的字符串(补码)
     */
    public static String getBinaryString(short value) {
        String str = "";
        for (int i = 16; i >= 1; i--) {
            str += (BitUtil.getBit(value, i) ? '1' : "0");
        }
        return str;
    }

    /**
     * 得到二进制字符串对应的十进制数值
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static byte getByteByBinary(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 8)
            throw new IllegalArgumentException("参数不是8位,byte类型是8位");
        byte rt = 0;
        if (binaryString.charAt(0) == '0') {// 正数
            for (int i = binaryString.length() - 1, j = 0; i > 0; i--, j++) {
                switch (binaryString.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return rt;
        } else if (binaryString.charAt(0) == '1') {// 负数
            StringBuffer sb = new StringBuffer(binaryString);
            minusOneThenReverse(sb);
            for (int i = sb.length() - 1, j = 0; i > 0; i--, j++) {// 求值
                switch (sb.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return (byte) -rt;
        } else {
            throw new IllegalArgumentException("符号位不正确...");
        }
    }

    /**
     * 得到二进制字符串对应的十进制数值,与getByteByBinary得有相同的功能,只是算法更好
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static int getByteByBinaryString(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 8)
            throw new IllegalArgumentException("参数不是8位,byte类型是8位");
        byte value = 0;
        for (int i = 1; i <= 8; i++) {
            value = BitUtil.setBit(value, i, binaryString.charAt(8 - i) == '1' ? true : false);
        }
        return value;
    }

    /**
     * 得到二进制字符串对应的十进制数值
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static int getIntByBinary(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 32)
            throw new IllegalArgumentException("参数不是32位,int类型是32位");
        int rt = 0;
        if (binaryString.charAt(0) == '0') {// 正数
            for (int i = binaryString.length() - 1, j = 0; i > 0; i--, j++) {
                switch (binaryString.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return rt;
        } else if (binaryString.charAt(0) == '1') {// 负数
            StringBuffer sb = new StringBuffer(binaryString);
            minusOneThenReverse(sb);
            for (int i = sb.length() - 1, j = 0; i > 0; i--, j++) {// 求值
                switch (sb.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return -rt;
        } else {
            throw new IllegalArgumentException("符号位不正确...");
        }
    }

    /**
     * 得到二进制字符串对应的十进制数值,与getIntByBinary得有相同的功能,只是算法更好
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static int getIntByBinaryString(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 32)
            throw new IllegalArgumentException("参数不是32位,int类型是32位");
        int value = 0;
        for (int i = 1; i <= 32; i++) {
            value = BitUtil.setBit(value, i, binaryString.charAt(32 - i) == '1' ? true : false);
        }
        return value;
    }

    /**
     * 得到二进制字符串对应的十进制数值
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static long getLongByBinary(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 64)
            throw new IllegalArgumentException("参数不是64位,long类型是64位");
        long rt = 0;
        if (binaryString.charAt(0) == '0') {// 正数
            for (int i = binaryString.length() - 1, j = 0; i > 0; i--, j++) {
                switch (binaryString.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return rt;
        } else if (binaryString.charAt(0) == '1') {// 负数
            StringBuffer sb = new StringBuffer(binaryString);
            minusOneThenReverse(sb);
            for (int i = sb.length() - 1, j = 0; i > 0; i--, j++) {// 求值
                switch (sb.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return -rt;
        } else {
            throw new IllegalArgumentException("符号位不正确...");
        }
    }

    /**
     * 得到二进制字符串对应的十进制数值,与getLongByBinary得有相同的功能,只是算法更好
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static long getLongByBinaryString(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 64)
            throw new IllegalArgumentException("参数不是64位,long类型是64位");
        long value = 0;
        for (int i = 1; i <= 64; i++) {
            value = BitUtil.setBit(value, i, binaryString.charAt(64 - i) == '1' ? true : false);
        }
        return value;
    }

    /**
     * 得到二进制字符串对应的十进制数值
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static short getShortByBinary(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 16)
            throw new IllegalArgumentException("参数不是16位,short类型是16位");
        short rt = 0;
        if (binaryString.charAt(0) == '0') {// 正数
            for (int i = binaryString.length() - 1, j = 0; i > 0; i--, j++) {
                switch (binaryString.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return rt;
        } else if (binaryString.charAt(0) == '1') {// 负数
            StringBuffer sb = new StringBuffer(binaryString);
            minusOneThenReverse(sb);
            for (int i = sb.length() - 1, j = 0; i > 0; i--, j++) {// 求值
                switch (sb.charAt(i)) {
                    case '1':
                        rt += 1 << j;
                        break;
                    case '0':
                        break;
                    default:
                        throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
                }
            }
            return (short) -rt;
        } else {
            throw new IllegalArgumentException("符号位不正确...");
        }
    }

    /**
     * 得到二进制字符串对应的十进制数值,与getShortByBinary得有相同的功能,只是算法更好
     *
     * @param binaryString 二进制字符串(补码)
     * @return 二进制对应的十进制数值
     */
    public static short getShortByBinaryString(String binaryString) throws IllegalArgumentException {
        if (binaryString.length() != 16)
            throw new IllegalArgumentException("参数不是16位,short类型是16位");
        short value = 0;
        for (int i = 1; i <= 16; i++) {
            value = BitUtil.setBit(value, i, binaryString.charAt(16 - i) == '1' ? true : false);
        }
        return value;
    }

    /**
     * 计数str中subStr的个数
     *
     * @param str
     * @param subStr
     * @return str中subStr的个数
     */
    public static int getSubStrCount(String str, String subStr) {
        assert str.length() >= subStr.length() : "str的长度须大于等于subStr的长度";
        int count = 0;
        // 从0开始
        int pos = str.indexOf(subStr, 0);
        while (-1 != pos) {
            count++;
            pos = str.indexOf(subStr, ++pos);// pos迭代之前加1
        }
        return count;
    }

    /**
     * 计数str中subStr的个数(采用递归实现)
     *
     * @param str
     * @param subStr
     * @param startPos 开始计数的位置
     * @return str中subStr的个数
     */
    public static int getSubStrCount(String str, String subStr, int startPos) {
        assert str.length() >= subStr.length() : "str的长度须大于等于subStr的长度";
        assert startPos >= 0;
        int pos = str.indexOf(subStr, startPos);
        if (-1 != pos) {
            countOfString++;
            pos = getSubStrCount(str, subStr, ++pos);
        } else {
            pos = countOfString;
            countOfString = 0;// 调完函数计数清零
        }
        return pos;
    }

    /**
     * 减1之后取反
     *
     * @param binaryString
     */
    private static void minusOneThenReverse(StringBuffer binaryString) throws IllegalArgumentException {
        for (int i = binaryString.length() - 1; i > 0; i--) {// 减1
            if (binaryString.charAt(i) == '1') {// 遇到'1'结束
                binaryString.setCharAt(i, '0');
                break;
            } else {
                binaryString.setCharAt(i, '1');
            }
        }
        for (int i = 1; i < binaryString.length(); i++) {// 取反
            switch (binaryString.charAt(i)) {
                case '1':
                    binaryString.setCharAt(i, '0');
                    break;
                case '0':
                    binaryString.setCharAt(i, '1');
                    break;
                default:
                    throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
            }
        }
    }

    /**
     * 取反之后加1
     *
     * @param binaryString
     */
    private static void reverseThenAddOne(StringBuffer binaryString) throws IllegalArgumentException {
        String str = binaryString.toString();
        binaryString.delete(0, binaryString.length());
        binaryString.append('1');// 负数符号位为1
        for (int i = 1; i < str.length(); i++) {// 取反
            switch (str.charAt(i)) {
                case '1':
                    binaryString.append('0');
                    break;
                case '0':
                    binaryString.append('1');
                    break;
                default:
                    throw new IllegalArgumentException("binaryString 不能转换(包含0,1之外的字符)");
            }
        }
        for (int i = binaryString.length() - 1; i > 0; i--) {// 加1
            if (binaryString.charAt(i) == '0') {// 遇到0结束
                binaryString.setCharAt(i, '1');
                break;
            } else {
                binaryString.setCharAt(i, '0');
            }
        }
    }

    /**
     * 字符串的反向(采用递归实现)
     *
     * @param oldStr 原来的字符串
     * @return 返回反向字符串
     */
    public static String reverse(String oldStr) {
        if (oldStr == null || oldStr.length() <= 1)// 为"",null,length==1时返回
            return oldStr;
        return reverse(oldStr.substring(1)) + oldStr.charAt(0);
    }

    /**
     * eg.
     * last_name--->LastName
     * last-name--->LastName
     *
     * @param str
     * @return
     */
    public static String getCamelClassName(String str) {
        Not.notEnpty(str);
        str = str.toLowerCase();
        String[] split = str.split("[-,_]");
        String ret = "";
        for (int i = 0; i < split.length; i++) {
            ret += split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
        }
        return ret;
    }

    /**
     * eg.
     * last_name--->lastName
     * last-name--->lastName
     *
     * @param str
     * @return
     */
    public static String getCamelProperty(String str) {
        Not.notEnpty(str);
        str = str.toLowerCase();
        String[] split = str.split("[-,_]");
        String ret = split[0];
        for (int i = 1; i < split.length; i++) {
            ret += split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
        }
        return ret;
    }

    /**
     * 截取字符串，从beginIndex开始，直到w.matches返回true,此时的位置为endIndex
     *
     * @param str
     * @param beginIndex
     * @param w
     * @return
     */
    public static String substringUntil(String str, int beginIndex, Condition w) {
        StringBuilder sb = new StringBuilder();
        for (int i = beginIndex; i < str.length(); i++) {
            if (w.matches(str.charAt(i), i + 1 == str.length() ? null : str.charAt(i + 1))) {
                break;
            }
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
