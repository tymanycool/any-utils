package com.tiany.util.algorithm;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MD5Util {
	/**
	 * MD5加密
	 * @param originalString 加密前的字符串
	 * @return 加密之后的字符串
	 */
	public static String md5(String originalString) {
		String signedOriginalString = null;
		try {
			byte[] signedOriginalStringBytes = MessageDigest.getInstance("MD5").digest(originalString.getBytes("UTF-8"));
			signedOriginalString = new HexBinaryAdapter().marshal(signedOriginalStringBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signedOriginalString;
	}
}
