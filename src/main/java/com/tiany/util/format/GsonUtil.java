package com.tiany.util.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class GsonUtil {
	public static Gson gson = new Gson();
	public static Gson gsonWithDate = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public static String toJsonStr(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T fromJsonStr(String jsonStr,Class<T> clazz) {
		return gson.fromJson(jsonStr, clazz);
	}

	public static String toJsonStrWithDate(Object obj) {
		return gsonWithDate.toJson(obj);
	}

	public static <T> T fromJsonStrWithDate(String jsonStr, Class<T> clazz) {
		return gsonWithDate.fromJson(jsonStr, clazz);
	}
	
}
