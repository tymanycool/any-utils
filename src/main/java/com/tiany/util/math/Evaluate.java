package com.tiany.util.math;

import com.tiany.util.StringUtil;

import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 计算表达式的工具类
 * 
 * <pre>
 * 用法举例:
 * <code>
 *   Evaluate eval = new Evaluate("-123+9-98.25/2-69.2");
 *   System.out.println(eval.get());//-232.325
 *   System.out.println(eval.get(2));//-232.33
 * </code>
 * </pre>
 * 
 * @author tianyao
 * 
 */
public class Evaluate {
	/**
	 * 保存计算的结果
	 */
	private double result;

	/**
	 * 通过表达式expression构造Evaluate对象
	 * 
	 * @param expression
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public Evaluate(String expression) throws IllegalArgumentException, NullPointerException {
		try {
			expression = this.check(expression);
			this.evaluate(expression);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (NullPointerException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalArgumentException("请检查表达式是否正确...");
		}
	}

	/**
	 * 得到计算的结果
	 * 
	 * @return 返回计算的结果
	 */
	public double get() {
		return this.result;
	}

	/**
	 * 得到保留n位有效小数后的结果
	 *
	 * @param n
	 *            需要保留n位小数
	 * @return 返回保留n位有效小数后的结果
	 */
	public double get(int n) {
		return MathUtil.round(this.result, n);
	}

	/**
	 * 计算表达式的值
	 * 
	 * @param expression
	 * @return 返回计算的结果
	 */
	private double evaluate(String expression) {
		Stack<Double> valueStack = new Stack<Double>();// 保存值的栈
		Stack<Character> operatorStack = new Stack<Character>();// 保存操作符的栈
		// 分词器:参数为true时表示操作符也作为一部分保存下来
		StringTokenizer tokens = new StringTokenizer(expression, "()+-/*%", true);
		while (tokens.hasMoreElements()) {
			String token = tokens.nextToken().trim();
			if (token.length() == 0) {// 如果是空字符串则返回继续循环
				continue;
			} else {
				char first = token.charAt(0);// 字符串的第一个字符
				if (first == '+' || first == '-') {
					// peek()查找栈顶元素但是不移除
					while (!operatorStack.isEmpty() && (operatorStack.peek() == '+' || operatorStack.peek() == '-'
							|| operatorStack.peek() == '*' || operatorStack.peek() == '/'
							|| operatorStack.peek() == '%')) {
						porcessAnOperator(valueStack, operatorStack);
					}
					operatorStack.push(first);
				} else if (first == '*' || first == '/' || first == '%') {// 取余和乘除的优先级一样
					while (!operatorStack.isEmpty() && (operatorStack.peek() == '*' || operatorStack.peek() == '/'
							|| operatorStack.peek() == '%')) {
						porcessAnOperator(valueStack, operatorStack);
					}
					operatorStack.push(first);
				} else if (first == '(') {// 左括号时直接入栈
					operatorStack.push('(');
				} else if (first == ')') {
					// 遇到右括号时,不断去栈顶的元素进行运算,直到遇到栈顶操作符为左括号,并弹出左括号
					while (operatorStack.peek() != '(') {
						porcessAnOperator(valueStack, operatorStack);
					}
					operatorStack.pop();// 弹出左括号
				} else {// 为数值时直接入值栈
					valueStack.push(new Double(token));
				}
			}
		}
		// 当读完tokens里面的字符串时,按顺序计算,计算的结果会保存在值栈的端部,此时值栈只有一个元素(结果),操作符栈没有元素
		while (!operatorStack.isEmpty()) {
			porcessAnOperator(valueStack, operatorStack);
		}
		return result = valueStack.pop();
	}

	/**
	 * 弹出值栈的两个值,弹出操作符上的一个操作符,做运算,把结果压入值栈内
	 * 
	 * @param valueStack
	 * @param operatorStack
	 */
	private void porcessAnOperator(Stack<Double> valueStack, Stack<Character> operatorStack) {
		char op = operatorStack.pop();
		double op1 = valueStack.pop();
		double op2 = valueStack.pop();
		if (op == '+')
			valueStack.push(op2 + op1);
		else if (op == '-')
			valueStack.push(op2 - op1);
		else if (op == '*')
			valueStack.push(op2 * op1);
		else if (op == '/')
			valueStack.push(op2 / op1);
		else if (op == '%') {
			valueStack.push(op2 % op1);
		}

	}

	/**
	 * 检查表达式是否正确
	 * 
	 * @param expression
	 * @return 返回优化后的表达式
	 */
	private String check(String expression) throws IllegalArgumentException, NullPointerException {
		if (null == expression) {
			throw new NullPointerException("表达式为null...");
		}
		if (0 == expression.length()) {
			throw new IllegalArgumentException("表达式为空字符串...");
		}
		expression = expression.replaceAll("\\s", "");// 删除无用的字符
		Pattern p = Pattern.compile("[^\\d\\.+\\-*/%\\(\\)]{1}");
		Matcher m = p.matcher(expression);
		// m.matches();会影响find(),如果是matches()之后调用find(),reset()一下
		m.reset();
		if (m.find()) {// find()匹配子串,而matches()匹配整个字符串
			int start = m.start();
			throw new IllegalArgumentException("表达式中含有非法的字符: " + expression.charAt(start));
		}
		int left = StringUtil.getSubStrCount(expression, "(");// 左括号的数目
		int right = StringUtil.getSubStrCount(expression, ")");// 右括号的数目
		if (left != right) {
			throw new IllegalArgumentException("(,)没有成对出现,检查是否多写或是少写括号...");
		}
		if (-1 != expression.indexOf("++")) {
			throw new IllegalArgumentException("表达式中有++,运算符不能连写...");
		}
		if (-1 != expression.indexOf("--")) {
			throw new IllegalArgumentException("表达式中有--,运算符不能连写...");
		}
		if (-1 != expression.indexOf("**")) {
			throw new IllegalArgumentException("表达式中有**,运算符不能连写...");
		}
		if (-1 != expression.indexOf("//")) {
			throw new IllegalArgumentException("表达式中有//,运算符不能连写...");
		}
		if (-1 != expression.indexOf("%%")) {
			throw new IllegalArgumentException("表达式中有%%,运算符不能连写...");
		}
		if (expression.matches(".*[+\\-*/%]{1}[+\\-*/%]{1}.*")) {// 判断操作是否有两个在一起的,其中-需要转义
			throw new IllegalArgumentException("运算符不能连写(.*[+\\-*/%]{1}[+\\-*/%]{1}.*)...");
		}
		if (expression.matches(".*[+\\-*/%]{1}$")) {// 如果最后一个是操作符,其中-需要转义
			throw new IllegalArgumentException("表达式不能以运算符结束...");
		}
		if (expression.matches(".*[+\\-*/%]{1}\\).*")) {// 判断操作是否有两个在一起的,其中-,)需要转义
			throw new IllegalArgumentException("表达式如下是不合法的:-),+),*),/),%)...");
		}
		StringBuffer sb = new StringBuffer(expression);
		for (int i = 0; i < sb.length(); i++) {
			switch (sb.charAt(i)) {
			case '+':
			case '-':
				if (i == 0) {// 如果一个操作符是+-/%并且是第一个元素,则在之前插入'0'
					sb.insert(i, '0');
				} else if (sb.charAt(i - 1) == '(') {// 如果一个操作符是+-/%并且左边是(,则在之前插入'0'
					sb.insert(i, '0');
				}
				break;
			case '/':
			case '%':
			case '*':
				if (i == 0) {// 首位不能是/,%,*
					throw new IllegalArgumentException(sb.charAt(i) + " 运算符出现在第一位...");
				} else if (sb.charAt(i - 1) == '(') {
					throw new IllegalArgumentException("(" + sb.charAt(i) + " 运算符缺少操作数...");
				}
				break;
			}
		}
		return sb.toString();
	}
}