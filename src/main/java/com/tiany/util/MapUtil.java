package com.tiany.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
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
     * 得到map对应得key(忽略大小写)
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
     * 对象转Map(转换本类属性，父类属性，不转换关联字段内的属性)
     *
     * @param object 目标对象
     * @return Map 集合
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> bean2Map(Object object){
        Map<String, String> ret = null;
        try {
            ret = BeanUtils.describe(object);
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        }
        return ret;
    }

    /**
     * 对象转换成Map
     * 1. 转换本类属性，父类属性，转换关联字段内的属性
     * 2. list类型不做处理，相同的key只保留一个
     * 3. 字段的值为null的字段时不会转化成map的key
     * @param obj
     * @return
     */
    public static Map<String, Object> obj2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return  obj2Map(obj,obj.getClass());
        }catch (Exception e) {
            throw new RuntimeException("object转换Map出错了!!!",e);
        }
    }

    private static Map obj2Map(Object obj, Class<?> cls) throws Exception{
        if (obj == null) {
            return null;
        }
        Map<String, Object> retMap = new HashMap<>();
        Class<?> superClas = cls.getSuperclass();
        // 是否有具有父类，不包含Object
        if (superClas != null && !superClas.equals(Object.class)) {
            Map map = obj2Map(obj, superClas);
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
                if(value == null){
                    continue;
                }
                if(ReflectUtil.isBasicType(propertyType)){
                    retMap.put(key, value);
                }else if(value instanceof List){
                    retMap.put(key, value);
                }else if(value instanceof Map){
                    // 只考虑一层结构
                    retMap.putAll((Map)value);
                }else {
                    Map map = obj2Map(value, value.getClass());
                    if (map != null) {
                        retMap.putAll(map);
                    }
                }
            }
        }
        return retMap;
    }

    /**
     * 根据map的key进行排序
     * @param map
     * @param isAsc 是否升序
     */
    public static Map<String,Object> sort(Map<String,? extends Object> map,final boolean isAsc){
        Map newMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int ret = o1.compareTo(o2);
                return isAsc?ret:-ret;
            }
        });
        newMap.putAll(map);
        return newMap;
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

    /**
     * 将空白字符转换成null统一处理
     * @param map
     * @return
     */
    public static Map<String,Object> fillBlank2Null(Map<String,Object> map){
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            if (StringUtil.isEmpty(String.valueOf(entry.getValue()))||"undefined".equals(entry.getValue())) {
                entry.setValue(null);
            }
        }
        return map;
    }


    /**
     *
     * 得到Map第一层级的字段(过滤Map类型)
     * @param map
     * @return
     *
     * @version 1.0
     * @since 1.0
     */
    public static Map getMapFields(Map map) {
        Map result = new HashMap();
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            if(!(next.getValue() instanceof Map)) {
                result.put(next.getKey(),next.getValue());
            }
        }
        return result;
    }

    /**
     *
     * 得到Map中key对应的值，如果Map中包含Map则递归查找
     * @return
     *
     * @version 1.0
     * @since 1.0
     */
    public static Object getObject(Map map,Object key) {
        Object ret = null;
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            if(next.getValue() instanceof Map) {
                if(key.equals(next.getKey())) {
                    ret = next.getValue();
                }else {
                    ret = getObject((Map)next.getValue(),key);
                }
            }else {
                if(key.equals(next.getKey())) {
                    ret = next.getValue();
                    return ret;
                }
            }
        }
        return ret;
    }


    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * 匿名处理
     * @param map 需要处理的map
     * @param sensitiveFileds 敏感字段集合
     * @return
     */
    public static Map anonymize(Map map,String ... sensitiveFileds) {
        Map retMap = new HashMap();
        List list = Arrays.asList(sensitiveFileds);
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            Object key = next.getKey();
            Object value = next.getValue();
            if (!(value instanceof Map)) {
                if(list.contains(key)) {
                    if(value instanceof String) {
                        String v = (String)value;
                        int length = v.length();
                        if(length>6) {
                            retMap.put(key, v.substring(0, 3)+"***"+v.substring(length-2,length));
                        }else {
                            retMap.put(key,"******");
                        }
                    }else {
                        retMap.put(key,"******");
                    }
                }else {
                    retMap.put(key,value);
                }
            }else {
                retMap.put(key, anonymize((Map)value,sensitiveFileds));
            }
        }
        return retMap;
    }

}
