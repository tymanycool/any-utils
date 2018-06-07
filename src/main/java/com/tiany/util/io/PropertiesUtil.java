package com.tiany.util.io;

import com.tiany.util.CastUtil;
import com.tiany.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public abstract class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	/**
	 * 得到属性的值（注意不是类路径下）
	 * @param filePath
	 * @param key
	 * @return
	 */
	public static String getProperty(String filePath,String key){
		File file = new File(filePath);
		return getProperty(file, key);
	}

	/**
	 * 设置属性的值（注意不是类路径下）
	 * @param filePath
	 * @param key
	 * @param value
	 */
	public static void setProperty(String filePath,String key,String value){
		File file = new File(filePath);
		setProperty(file, key,value);
	}

	/**
	 * 得到属性的值（注意不是类路径下）
	 * @param file
	 * @param key
	 * @return
	 */
	public static String getProperty(File file,String key){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			Properties pro = new OrderedProperties();
			pro.load(fis);
			return pro.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 设置属性的值（注意不是类路径下）
	 * @param file
	 * @param key
	 * @param value
	 */
	public static void setProperty(File file,String key,String value){
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			Properties pro = new OrderedProperties();
			fis = new FileInputStream(file);
			pro.load(fis);
			pro.setProperty(key,value);
			fos = new FileOutputStream(file);
			pro.store(fos, DateUtil.thisDate());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 加载属性文件
	 * @param fileName 类路径下的文件名
	 * @return Properties Properties对象
	 */
	public static Properties loadProps(String fileName) {
		Properties props = null;
		InputStream is = null;
		Reader reader = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (is == null) {
				throw new FileNotFoundException(fileName + " file is not found");
			}
			props = new Properties();
			reader = new InputStreamReader(is,"UTF-8");
			props.load(reader);
		} catch (IOException e) {
			logger.error("load properties file failure", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("close input stream failure", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("close input stream failure", e);
				}
			}
		}
		return props;
	}

	/**
	 * 是否包含某属性
	 * @param props 属性文件
	 * @param key 关键属性
	 * @return true 包含 false 不包含
	 */
	public static boolean containsKey(Properties props, String key) {
		return props.containsKey(key);
	}

	/**
	 * 获取字符型属性
	 * @param key 关键属性
	 * @param props 属性文件
	 * @return String属性值
	 */
	public static String getString(Properties props, String key) {
		String value = "";
		if (props.containsKey(key)) {
			value = props.getProperty(key);
		}
		return value;
	}

	/**
	 * 获取字符型属性（带有默认值）
	 * @param key 关键属性
	 * @param props 属性文件
	 * @param defaultValue 默认值
	 * @return String string类型值
	 */
	public static String getString(Properties props, String key, String defaultValue) {
		String value = defaultValue;
		if (props.containsKey(key)) {
			value = props.getProperty(key);
		}
		return value;
	}

	/**
	 *  获取数值型属性
	 * @param props 属性文件对象
	 * @param key 关键属性
	 * @return int int类型值
	 */
	public static int getInt(Properties props, String key) {
		int value = 0;
		if (props.containsKey(key)) {
			value = CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取数值型属性（带有默认值）
	 * @param props 属性文件
	 * @param key 关键属性值
	 * @param defaultValue 默认值
	 * @return int int类型值
	 */
	public static int getInt(Properties props, String key, int defaultValue) {
		int value = defaultValue;
		if (props.containsKey(key)) {
			value = CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取布尔型属性
	 * @param props Properties对象
	 * @param key 关键属性
	 * @return boolean boolean类型值
	 */
	public static boolean getBoolean(Properties props, String key) {
		return getBoolean(props, key, false);
	}

	/**
	 * 获取布尔型属性（带有默认值）
	 * @param props 属性文件
	 * @param key 关键属性值
	 * @param defaultValue 默认值
	 * @return boolean boolean类型值
	 */
	public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
		boolean value = defaultValue;
		if (props.containsKey(key)) {
			value = CastUtil.castBoolean(props.getProperty(key));
		}
		return value;
	}
}
