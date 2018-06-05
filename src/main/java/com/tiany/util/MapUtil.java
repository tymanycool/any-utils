package com.tiany.util;

import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Map工具类
 * @author tianyao
 * @version 1.0
 * @since 1.0
 */
public abstract class MapUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

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

    /**
     * Bean转换成Map(list类型不做处理，相同的key只保留一个)
     * @param obj
     * @return
     */
    public static Map<String, Object> bean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return  bean2Map(obj,obj.getClass());
        }catch (Exception e) {
            throw new RuntimeException("Bean转换Map出错了!!!",e);
        }
    }

    private static Map bean2Map(Object obj, Class<?> cls) throws Exception{
        if (obj == null) {
            return null;
        }
        Map<String, Object> retMap = new HashMap<>();
        Class<?> superClas = cls.getSuperclass();
        // 是否有具有父类，不包含Object
        if (superClas != null && !superClas.equals(Object.class)) {
            Map map = bean2Map(obj, superClas);
            if(map != null){
                retMap.putAll(map);
            }
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            // 过滤class属性
            if (!key.equals("class")) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Class<?> propertyType = property.getPropertyType();
                Object value = getter.invoke(obj);
                if(ReflectUtil.isBasicType(propertyType)){
                    if(value != null){
                        retMap.put(key, value);
                    }
                }else if(value instanceof List){
                    if(value != null){
                        retMap.put(key, value);
                    }
                }else {
                    Map map = bean2Map(value, value.getClass());
                    if(map != null){
                        retMap.putAll(map);
                    }
                }
            }
        }
        return retMap;
    }


    /**
     * 解析2层xml
     * @param map
     * @param node
     * @return
     */
    private static  Map<String,Object> getNodes(Map<String,Object> map,Element node){
        map.put(node.getName(), node.getTextTrim());
        List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list
        for(Attribute attr:listAttr){//遍历当前节点的所有属性
            String name=attr.getName();//属性名称
            String value=attr.getValue();//属性的值
            logger.debug("属性名称："+name+"属性值："+value);
        }

        //递归遍历当前节点所有的子节点
        List<Element> listElement=node.elements();//所有一级子节点的list
        for(Element e:listElement){//遍历所有一级子节点
            getNodes(map,e);//递归
        }
        return map;
    }

    /**
     * 将xml转换成map对象(相同的key只保留一个)
     * @param xmlStr
     * @return
     */
    public static Map<String,Object> xml2Map(String xmlStr){
        if (null == xmlStr || "".equals(xmlStr)) {
            return null;
        }
        Map<String,Object> map = new HashMap<String,Object>();

        try {
            // 将xml格式的字符串转换成Document对象
            Document doc = DocumentHelper.parseText(xmlStr);
            Element root=doc.getRootElement();//获取根节点
            getNodes(map,root);//从根节点开始遍历所有节点
            return map;
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.error("将xml转换成map对象转换失败！！！");
        }
        return null;
    }
}
