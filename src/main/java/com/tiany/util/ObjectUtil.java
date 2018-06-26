package com.tiany.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public abstract class ObjectUtil {
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * 数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     * @param bytes
     * @return
     */
    public static boolean isEmpty(byte[] bytes) {
        return (null == bytes || bytes.length == 0);
    }


    /**
     * 通过序列化与反序列化得到一个对象的副本（深复制）
     *
     * @param <T> T 类型需满足：class T implements Serializable
     * @param obj 要复制的对象
     * @return 返回一个副本
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);//序列化

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();//反序列化

        // 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
    }

    /**
     * 比较对象相等，如果有一个相等则返回ture
     *
     * @param dest
     * @param arr
     * @return
     */
    public static boolean equals(Object dest, Object... arr) {
        for (Object s : arr) {
            if (s.equals(dest))
                return true;
        }
        return false;
    }

    /**
     * serialize
     * (获取对象序列化byte数组)
     *
     * @param object obj
     * @return byte[]
     * @author Administrator
     */
    public static byte[] serialize(Object object) {
        byte[] bytes = null;

        if (null == object) {
            bytes = new byte[0];
        }

        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException(ObjectUtil.class.getSimpleName() + " requires a Serializable object, but " + object.getClass().getName() + " not a Serializable object");
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(object);
            oos.flush();
            bytes = os.toByteArray();
        } catch (IOException e) {
            logger.error("Failed to serialize", e);
        }

        return bytes;
    }

    /**
     * deserialize
     * (将byte[]反序列化为对象)
     *
     * @param bytes byte
     * @param <T>   实体类
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {
        T object = null;

        if (!isEmpty(bytes)) {
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream ois = new ObjectInputStream(is);
                object = (T) ois.readObject();
            } catch (IOException e) {
                logger.error("Failed to deserialize", e);
            } catch (ClassNotFoundException ex) {
                logger.error("Failed to deserialize object", ex);
            }
        }

        return object;
    }

}
