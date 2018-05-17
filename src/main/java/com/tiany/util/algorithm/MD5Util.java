package com.tiany.util.algorithm;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
	 * MD5加密
	 * @param originalString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalArgumentException
	 */
	public static String checkMD5(String originalString)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalArgumentException {
		byte[] signedOriginalStringBytes = MessageDigest.getInstance("MD5").digest(originalString.getBytes("UTF-8"));
		String signedOriginalString = new HexBinaryAdapter().marshal(signedOriginalStringBytes);
		return signedOriginalString;
	}
}
