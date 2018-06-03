package com.tiany.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Map工具类
 */
public abstract class MapUtil {
    /**
     * map中是否包含key(忽略大小写)
     * @param map
     * @param key
     * @return
     */
    public static Object getIgnoreCase(Map<String,Object> map,String key){
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            if(Objects.toString(next.getKey()).toUpperCase().equals(Objects.toString(key).toUpperCase())){
                return next.getValue();
            };
        }
        return null;
    }
    /**
     * map中是否包含key(忽略大小写)
     * @param map
     * @param key
     * @return
     */
    public static boolean containsKeyIgnoreCase(Map<String,Object> map,String key){
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(Objects.toString(next).toUpperCase().equals(key.toUpperCase())){
                return true;
            };
        }
        return false;
    }

    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        }catch (Exception e) {
            throw new RuntimeException("Bean转换Map出错了!!!",e);
        }
        return map;
    }
}
