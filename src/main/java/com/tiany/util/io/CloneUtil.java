package com.tiany.util.io;

import java.io.*;

/**
 * 复制的工具类
 * 
 * @author tianyao
 *
 */
public class CloneUtil {

	private CloneUtil() {
		throw new AssertionError();
	}

	/**
	 * 通过序列化与反序列化得到一个对象的副本（深复制）
	 * 
	 * @param <T>
	 *            T 类型需满足：class T implements Serializable
	 * @param obj
	 *            要复制的对象
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
}