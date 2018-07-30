package com.tiany.util.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tiany.util.ReflectUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * xml工具
 * @author tianyao
 * @version 1.0
 */
public abstract class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 将对象直接转换成String类型的 XML输出
     * @param obj
     * @return
     */
    public static String obj2Xml(Object obj){
        try {
            ObjectMapper mapper = new XmlMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将对象直接转换成String类型的 XML输出
     * 实体类需要加@XmlRootElement
     * @param obj
     * @return
     */
    public static String obj2Xml2(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            logger.error("object转换xml失败...",e);
        }
        return sw.toString();
    }

    /**
     * 将实体bean转换成xmlStr
     *
     * @param bean
     * @return xml字符串类型
     */
    public static String bean2Xml(Object bean) {
        try {
            return bean2Xml(bean, bean.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("bean转换xml失败...",e);
        }
        return null;
    }

    /**
     * 解析实体生成xml格式
     *
     * @param bean
     *            实体对象
     * @param cls
     *            class类型
     * @return xml字符串
     * @throws Exception
     */
    private static String bean2Xml(Object bean, Class<?> cls) throws Exception {
        if(bean == null){
            return "";
        }else if (bean instanceof List){
            // 如果是list
            return list2Xml((List)bean,"xml");
        }
        StringBuffer strbuf = new StringBuffer();
        Class<?> superClas = cls.getSuperclass();
        // 是否有具有父类，不包含Object,List,Map
        if (superClas != null && !superClas.equals(Object.class)&&!superClas.equals(List.class)&&!superClas.equals(Map.class)) {
            strbuf.append(bean2Xml(bean, superClas));
        }
        Field[] fields = cls.getDeclaredFields();
        Field field;
        // xml节点node
        String fieldXmlNode;
        // 对应xml节点node的bean
        Object fieldXmlVal;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            // TODO 控制xml标签的大小写
            fieldXmlNode = field.getName();
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), bean.getClass());
            // 得到get方法
            Method method = pd.getReadMethod();

            // 存在get方法时
            if (method != null) {
                fieldXmlVal = method.invoke(bean);
                // 属性值为null时不生成xml(返回是"")
                if(fieldXmlVal == null) {
                    continue;
                }
                if (fieldXmlVal instanceof java.util.List) {
                    // 如果是list类型
                    List childList = (List) fieldXmlVal;
                    try {
                        for (Object child : childList) {
                            // 基本类型
                            if(ReflectUtil.isBasicType(child.getClass())){
                                resolveBasicType(strbuf, fieldXmlNode, child);
                                continue;
                            }else if(!(child instanceof List || child instanceof Map)){
                                // 排除list和map的情况
                                // 是Entity类型时
                                resolveEntityType(strbuf, fieldXmlNode, child);
                            }else{
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(ReflectUtil.isBasicType(fieldXmlVal.getClass())){
                    // 基本类型时
                    resolveBasicType(strbuf, fieldXmlNode, fieldXmlVal);
                } else {
                    // 是Entity类型时
                    resolveEntityType(strbuf, fieldXmlNode, fieldXmlVal);
                }
            } else {
                // 不存在get方法
                strbuf.append("<").append(fieldXmlNode).append(">");
                strbuf.append("");
                strbuf.append("</").append(fieldXmlNode).append(">");
            }
        }
        return strbuf.toString();
    }

    private static void resolveBasicType(StringBuffer strbuf, String fieldXmlNode, Object value) {
        strbuf.append("<").append(fieldXmlNode).append(">");
        strbuf.append(value == null ? "" : String.valueOf(value));
        strbuf.append("</").append(fieldXmlNode).append(">");
    }

    private static void resolveEntityType(StringBuffer strbuf, String fieldXmlNode, Object fieldXmlVal) throws Exception {
        strbuf.append("<").append(fieldXmlNode).append(">");
        strbuf.append(bean2Xml(fieldXmlVal, fieldXmlVal.getClass()));
        strbuf.append("</").append(fieldXmlNode).append(">");
    }

    /**
     * 将list转换成xml标签格式字符串
     * eg. list2Xml([1,2,3],"MyName"): --> <MyName>1</MyName><MyName>2</MyName><MyName>3</MyName>
     * @param list
     *            list里的实体对象
     * @param name
     *            字段名称
     * @return String xml格式字符串
     */
    public static <T> String list2Xml(List list, String name) {
        if (null == list) {
            return "";
        }
        StringBuffer strbuf = new StringBuffer();
        try {
            for (Object obj : list) {
                // 基本类型
                if(ReflectUtil.isBasicType(obj.getClass())){
                    resolveBasicType(strbuf, name, obj);
                    continue;
                } else if(!(obj instanceof List || obj instanceof Map)){
                    // 排除list和map的情况
                    // 是Entity类型时
                    resolveEntityType(strbuf, name, obj);
                }
            }
            return strbuf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }




}
