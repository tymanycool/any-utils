package com.tiany.util.validate;

import com.tiany.util.ObjectUtil;
import com.tiany.util.ReflectUtil;
import com.tiany.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 校验工具类
 *
 */
public abstract class ValidateUtil {
	/** 常用正则表达式：匹配用户名 */
	private final static String REG_USER_NAME = "^[a-zA-Z]\\w{3,31}";

	/** 常用正则表达式：匹配email地址 */
	public final static String REG_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	//private static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	/** 常用正则表达式：匹配非负整数（正整数 + 0）*/
	public final static String REG_INTEGER_1 = "^\\d+$";
	/** 常用正则表达式：匹配正整数 */
	public final static String REG_INTEGER_2 = "^[0-9]*[1-9][0-9]*$";
	/** 常用正则表达式：匹配非正整数（负整数 + 0）*/
	public final static String REG_INTEGER_3 = "^((-\\d+) ?(0+))$";
	/** 常用正则表达式：匹配负整数 */
	public final static String REG_INTEGER_4 = "^-[0-9]*[1-9][0-9]*$";
	/** 常用正则表达式：匹配整数 */
	public final static String REG_INTEGER_5 = "^-?\\d+$";

	/** 常用正则表达式：匹配非负浮点数（正浮点数 + 0）*/
	public final static String REG_FLOAT_1 = "^\\d+(\\.\\d+)?$";
	/** 常用正则表达式：匹配正浮点数 */
	public final static String REG_FLOAT_2 = "^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$";
	/** 常用正则表达式：匹配非正浮点数（负浮点数 + 0）*/
	public final static String REG_FLOAT_3 = "^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$";
	/** 常用正则表达式：匹配负浮点数 */
	public final static String REG_FLOAT_4 = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$";
	/** 常用正则表达式：匹配浮点数 */
	public final static String REG_FLOAT_5 = "^(-?\\d+)(\\.\\d+)?$";

	/** 常用正则表达式：匹配由26个英文字母组成的字符串 */
	public final static String REG_LETTER_1 = "^[A-Za-z]+$";
	/** 常用正则表达式：匹配由26个英文字母的大写组成的字符串 */
	public final static String REG_LETTER_2 = "^[A-Z]+$";
	/** 常用正则表达式：匹配由26个英文字母的小写组成的字符串 */
	public final static String REG_LETTER_3 = "^[a-z]+$";
	/** 常用正则表达式：匹配由数字和26个英文字母组成的字符串 */
	public final static String REG_LETTER_4 = "^[A-Za-z0-9]+$";
	/** 常用正则表达式：匹配由数字、26个英文字母或者下划线组成的字符串 */
	public final static String REG_LETTER_5 = "^\\w+$";

	/** 常用正则表达式：匹配url */
	public final static String REG_URL_1 = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";
	/** 常用正则表达式：匹配url */
	public final static String REG_URL_2 = "[a-zA-z]+://[^\\s]*";

	/** 常用正则表达式：匹配中文字符 */
	public final static String REG_CHINESE_1 = "[\\u4e00-\\u9fa5]";
	/** 常用正则表达式：匹配双字节字符(包括汉字在内) */
	public final static String REG_CHINESE_2 = "[^\\x00-\\xff]";

	/** 常用正则表达式：匹配空行 */
	public final static String REG_LINE = "\\n[\\s ? ]*\\r";

	/** 常用正则表达式：匹配HTML标记 */
	public final static String REG_HTML = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/";

	/** 常用正则表达式：匹配首尾空格 */
	public final static String REG_START_END_EMPTY = "(^\\s*) ?(\\s*$)";

	/** 常用正则表达式：匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线) */
	public final static String REG_ACCOUNT_NUMBER = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

	/** 常用正则表达式：匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822 */
	public final static String REG_TELEPHONE = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}";

	/** 常用正则表达式：腾讯QQ号, 腾讯QQ号从10000开始 */
	public final static String REG_QQ = "[1-9][0-9]{4,}";

	/** 常用正则表达式：匹配中国邮政编码 */
	public final static String REG_POST_BODY = "[1-9]\\d{5}(?!\\d)";

	/** 常用正则表达式：匹配身份证, 中国的身份证为15位或18位 */
	public final static String REG_ID_CARD = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";

	/** 常用正则表达式：IP */
	public final static String REG_IP = "\\d+\\.\\d+\\.\\d+\\.\\d+";

	/** 常用正则表达式：手机号码 */
	public final static String REG_PHONE = "^1[34578]{1}[0-9]{9}$";

	/** 常用正则表达式：密码包含字母和数字，长度为6-20位 */
	public final static String REG_PASSWORD = "^(?![^0-9]+$)(?![^a-zA-Z]+$).+$";

	/** 常用正则表达式：匹配企业证件号码,1-20位 */
	public final static String REG_OCC_CODE_NO = "^.{1,20}$";
	
	/**
	 * 验证必填项,如果不通过,返回List【错误描述信息】
	 * 
	 * @param dataObj 校验对象
	 * @param propNames 校验属性【数组】
	 * @param displayNames 属性对应的【中文名称】
	 * @return errorList 错误集合,每条数据表示一个属性的错误描述信息
	 */
	public static List<String> validateMust(Object dataObj, String[] propNames,
			String[] displayNames) {
		AssertUtil.isTrue(propNames.length == displayNames.length, "参数名与显示名的个数不一致");
		List<String> errorList = new ArrayList<String>();
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			String displayName = displayNames[i];
			Object value = ReflectUtil.getFieldValue(dataObj, propName);
			if (null == value) {
				errorList.add(displayName + "(" + propName + ")是必填项");
			} else {
				if (value instanceof String) {
					// 处理全角空格
					String valueStr = value.toString().replace("　", "").trim();
					if (valueStr.length() == 0) {
						errorList.add(displayName + "(" + propName + ")是必填项");
					}
				}
			}
		}
		return errorList;
	}

	/**
	 * 验证必填项,如果不通过,返回List【错误描述信息】
	 *
	 * @param dataMap 校验Map
	 * @param propNames 校验属性【数组】
	 * @param displayNames 属性对应的【中文名称】
	 * @return errorList 错误集合,每条数据表示一个属性的错误描述信息
	 */
	public static List<String> validateMust(Map dataMap, String[] propNames,
											String[] displayNames) {
		if(propNames.length != displayNames.length) {
			throw new IllegalArgumentException("参数名与显示名的个数不一致");
		}
		List<String> errorList = new ArrayList<String>();
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			String displayName = displayNames[i];
			Object value = dataMap.get(propName);
			if (null == value) {
				errorList.add(displayName + "(" + propName + ")是必填项");
			} else {
				if (value instanceof String) {
					// 处理全角空格
					String valueStr = value.toString().replace("　", "").trim();
					if (valueStr.length() == 0) {
						errorList.add(displayName + "(" + propName + ")是必填项");
					}
				}
			}
		}
		return errorList;
	}

	/**
	 * 验证必填项,如果不通过,返回List【错误描述信息】
	 *
	 * @param wrappers 校验wrappers
	 * @return errorList 错误集合,每条数据表示一个属性的错误描述信息
	 */
	public static List<String> validateMustAndEnums(List<ValidateWrapper> wrappers) {
		List<String> errorList = new ArrayList<String>();
		for (int i = 0; i < wrappers.size(); i++) {
			ValidateWrapper validateWrapper = wrappers.get(i);
			String propName = validateWrapper.getKey();
			String displayName = validateWrapper.getDisplayName();
			Object value = validateWrapper.getValue();
			if (null == value) {
				errorList.add(displayName + "(" + validateWrapper.getKey() + ")是必填项");
			} else {
				if (value instanceof String) {
					// 处理全角空格
					String valueStr = value.toString().replace("　", "").trim();
					if (valueStr.length() == 0) {
						errorList.add(displayName + "(" + propName + ")是必填项");
					}else{
						Object[] enums = validateWrapper.getEnums();
						if(enums.length>0){
							boolean iseq = false;
							for (Object obj : enums) {
								if (obj.equals(value)){
									iseq = true;
									break;
								}
							}
							if(!iseq){
								errorList.add(displayName + "(" + propName + ")是必需是("+ Arrays.toString(enums) +")其中一个值");
							}
						}
					}
				}
			}
		}
		return errorList;
	}
	
	/**
	 * 
	 * 校验邮箱
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean validateEmail(String email) {
		if (null == email || email.trim().length() == 0) {
			return false;
		}
		return email.matches(REG_EMAIL);
	}

	/**
	 * 
	 * 校验用户名
	 * 
	 * @param
	 * @return boolean
	 */
	public static boolean validateUserName(String userName) {
		return validateByRegex(userName, REG_USER_NAME);
	}

	/**
	 * 按指定正则表达式验证
	 * @param input 输入字符
	 * @param regex 正则
	 * @return 成立与否
	 */
	public static boolean validateByRegex(String input, String regex) {
		if (null == input || input.trim().length() == 0) {
			return false;
		}
		return input.matches(regex);
	}

}
