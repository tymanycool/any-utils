package com.tiany.util.io;

import com.tiany.util.DateUtil;

import java.io.*;
import java.util.Properties;

public abstract class PropertiesUtil {
	
	public static String getProperty(String filePath,String key){
		File file = new File(filePath);
		return getProperty(file, key);
	}
	
	public static void setProperty(String filePath,String key,String value){
		File file = new File(filePath);
		setProperty(file, key,value);
	}
	
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
}
