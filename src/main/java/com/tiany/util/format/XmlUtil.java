package com.tiany.util.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * xml工具
 */
public class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 将对象直接转换成String类型的 XML输出
     * 实体类需要加@XmlRootElement
     * @param obj
     * @return
     */
    public static String obj2Xml(Object obj) {
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
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * 将实体bean转换成xmlStr
     *
     * @param obj
     * @return xml字符串类型
     */
    public static <T> String bean2Xml(T obj) {
        try {
            return convertXml(obj, obj.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("转换失败");
        }
        return null;
    }

    /**
     * 解析实体生成xml格式
     *
     * @param obj
     *            实体对象
     * @param clas
     *            class类型
     * @return xml字符串
     * @throws Exception
     */
    public static String convertXml(Object obj, Class<?> clas) throws Exception {
        StringBuffer strbuf = new StringBuffer();
        Class<?> superClas = clas.getSuperclass();
        // 是否有具有父类，不包含Object
        if (superClas != null && !superClas.equals(Object.class)) {
            strbuf.append(convertXml(obj, superClas));
        }
        Field[] fields = clas.getDeclaredFields();
        Field field;
        // xml节点node
        String fieldXmlNode;
        // 对应xml节点node的bean
        Object fieldXmlVal;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            fieldXmlNode = field.getName().toUpperCase();
            if ("SERIALVERSIONUID".equalsIgnoreCase(fieldXmlNode)) {
                continue;
            }
            if ("transcode".equalsIgnoreCase(fieldXmlNode)) {
                continue;
            }
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
            Method method = pd.getReadMethod();

            if (method != null) {
                fieldXmlVal = method.invoke(obj);
                if (fieldXmlVal instanceof java.util.List) {
                    strbuf.append(list2Xml(fieldXmlVal, fieldXmlNode));
                } else {
                    strbuf.append("<").append(fieldXmlNode).append(">");
                    strbuf.append(fieldXmlVal == null ? "" : String.valueOf(fieldXmlVal));
                    strbuf.append("</").append(fieldXmlNode).append(">");
                }

            } else {
                strbuf.append("<").append(fieldXmlNode).append(">");
                strbuf.append("");
                strbuf.append("</").append(fieldXmlNode).append(">");
            }

        }
        return strbuf.toString();
    }

    /**
     * 将list转换成xml标签格式字符串
     *
     * @param obj
     *            list里的实体对象
     * @param name
     *            字段名称
     * @return String xml格式字符串
     */
    public static <T> String list2Xml(Object obj, String name) {

        if (null == obj) {
            return "";
        }
        StringBuffer strbuf = new StringBuffer();
        @SuppressWarnings("unchecked")
        List<T> childList = (ArrayList<T>) obj;
        try {
            for (T t : childList) {
                Field[] childProperties = t.getClass().getDeclaredFields();// 获得子类的所有属性
                strbuf.append("<").append(name).append(">");
                for (int j = 0; j < childProperties.length; j++) {
                    String childMethodName = childProperties[j].getName().toUpperCase();
                    if (!"SERIALVERSIONUID".equals(childMethodName)) {
                        Method methChild = null;
                        methChild = t.getClass().getMethod("get" + childMethodName.substring(0, 1).toUpperCase()
                                + childProperties[j].getName().substring(1));

                        // 为二级节点添加属性，属性值为对应属性的值
                        strbuf.append("<").append(childProperties[j].getName().toUpperCase()).append(">");

                        if (null == methChild.invoke(t)) {
                            strbuf.append("");
                        } else {
                            strbuf.append(methChild.invoke(t).toString());
                        }

                        strbuf.append("</").append(childProperties[j].getName().toUpperCase()).append(">");
                    }
                }
                strbuf.append("</").append(name).append(">");
            }
            return strbuf.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;

    }
}
