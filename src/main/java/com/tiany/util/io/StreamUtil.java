package com.tiany.util.io;


import java.io.*;

/**
 * 流工具
 * @author tianyao
 *
 */
public class StreamUtil {
	/**
	 * 输入流转化成string
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static String streamToString(InputStream is) throws Exception{
		BufferedReader reader=new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		String line;
		while((line=reader.readLine())!=null){
			builder.append(line);
			builder.append("\r\n");
		}
		return builder.toString();
	}
	/**
	 * 输入流转化成byte[]
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static byte[] streamToByteArray(InputStream is) throws Exception{
		ByteArrayOutputStream  baos = new ByteArrayOutputStream();
		byte[] b=new byte[1024];
		int len;
		BufferedInputStream bis=new BufferedInputStream(is);
		while((len=bis.read(b))!=-1){
			baos.write(b, 0, len);
		}
		return baos.toByteArray();
	}

}
