package com.tiany.util.algorithm;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 该类是用来做签名和验签的 （1）签名处理： 1.请将报文使用md5提取报文摘要。 2.再将摘要用已方私钥进行签名。 （2）验签处理
 * 
 * @author tiany
 * @version 1.0
 */

@SuppressWarnings("restriction")
public class RSAUtil {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";
	public static final String ENCODING = "UTF-8";
	/** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	public static final int KEY_SIZE = 1024;

	/**
	 * 公钥
	 */
	public static String publicString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSSev3EN/xIKSFyuf01wYyBYw7R5wHlwNQkco1\r\n"
			+ "nYh8KuUvhoeTiAdvcsbkKKi2Vpw2bfY/LzJcpiBVKPEoqM8+PlRcaloW3qJkQYOnuK+FY7iZiusR\r\n"
			+ "oNeBkHJS9XWD8yt8iqTys2mtFkx+irDvrDowXlCaKzQ/qddkrqHd5gtcHQIDAQAB\r\n";
	/**
	 * 私钥
	 */
	public static String privateString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJJJ6/cQ3/EgpIXK5/TXBjIFjDtH\r\n"
			+ "nAeXA1CRyjWdiHwq5S+Gh5OIB29yxuQoqLZWnDZt9j8vMlymIFUo8Siozz4+VFxqWhbeomRBg6e4\r\n"
			+ "r4VjuJmK6xGg14GQclL1dYPzK3yKpPKzaa0WTH6KsO+sOjBeUJorND+p12Suod3mC1wdAgMBAAEC\r\n"
			+ "gYBY0LKHXOJpdgUNCcTMPKQRSJb8ApzzarbjerAG3y7XG//rW8UCSCsjec7RBPJxSaIr1ZUUhWYR\r\n"
			+ "I/crXOEVa2/ukYU2U8x20iqxCQJSiswANXGucUt6u3YnBVocqclCI4qYYtK87B/SwiGpTdO6E6tT\r\n"
			+ "KddfAQslPL7lPN8Iad4grQJBANI1CGZX6V8tMOfwHmhA6QkOSamycmYlZly/ar6DXP+x+fGggty7\r\n"
			+ "ZaO409GbpK92Pn92EjWBbVx4+JkMGQ+L2icCQQCyKD8hiqgpzlDWrO8cd9SfXhpN9ItXpIv9+YAL\r\n"
			+ "GwVhTBmRQdgRvVKIlapACpFzBmo3XkrwB32F3UlmhAxJlBYbAkEAx5WE6S9VLIIK6l1pcZEX8fo3\r\n"
			+ "5MDCr8uY2j0Ay+yME1bXuxysw4nGYmHt9Bm0jshVZM4lUdoWjPNrDl2XOdSOhwJABVumgmi8I/27\r\n"
			+ "eqlay51i/KQE4IFRS4J8X1gInMzkPYffYPn8+JOcDtWJeEGZHD9+JEomsM9Y3BTKp1WjVPcqLwJA\r\n"
			+ "WYGD343Ru5AVTFUnZYe2qVb+PAngg8KeNFZEabkz/JZgEDe2216c0C8YjGFIMCqV103zpBtq7WOa\r\n" + "OzUy6WlVFg==";

	


	/**
	 * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
	 * 
	 * @return
	 */
	public static Map<String, byte[]> generateKeyBytes() {

		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGenerator.initialize(KEY_SIZE);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
			keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
			keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
			return keyMap;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 公钥，X509EncodedKeySpec 用于构建公钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	public static PublicKey restorePublicKey(byte[] keyBytes) {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 得到公钥对象
	 * 
	 * @param publicKey
	 * @return
	 *
	 * @version 1.0
	 * @since 1.0
	 */
	public static PublicKey getPublicKey(String publicKey) {
		try {
			return restorePublicKey(decryptBASE64(publicKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	public static PrivateKey restorePrivateKey(byte[] keyBytes) {

		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory factory;
		try {
			factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);// 1276505547
			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * 得到私钥对象
	 * 
	 * @param privateKey
	 * @return
	 *
	 * @version 1.0
	 * @since 1.0
	 */
	public static PrivateKey getPrivateKey(String privateKey) {
		try {
			return restorePrivateKey(decryptBASE64(privateKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 私钥签名
	 * @author tiany
	 * @param privateKey
	 * @param md5Result
	 * @return
	 *
	 * @version 1.0
	 * @since 1.0
	 */
	public static String RSAEncode(String privateKey,String md5Result) {
		PrivateKey prikey = getPrivateKey(privateKey);
		try {
			byte[] rsaEncode = RSAEncode(prikey, md5Result.getBytes(ENCODING));
			return encryptBASE64(rsaEncode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 签名
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 */
	public static byte[] RSAEncode(PrivateKey key, byte[] plainText) {

		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plainText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 解密(验签)
	 * 
	 * @author tiany
	 * @param encodedText
	 * @return
	 *
	 * @version 1.0
	 * @since 1.0
	 */
	public static String RSADecode(String publicKey, String encodedText) {
		try {
			PublicKey pubkey = getPublicKey(publicKey);
			byte[] decryptBASE64 = decryptBASE64(encodedText);
			return RSADecode(pubkey, decryptBASE64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验签
	 * 
	 * @param key
	 * @param encodedText
	 * @return
	 */
	public static String RSADecode(PublicKey key, byte[] encodedText) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(encodedText));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * Base64解码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 *
	 * @version 1.0
	 * @since 1.0
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * 
	 * Base64编码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 *
	 * @version 1.0
	 * @since 1.0
	 */
	public static String encryptBASE64(byte[] key) throws Exception {

		return (new BASE64Encoder()).encodeBuffer(key);
	}



	// MD5摘要
	public static String MD5(String srcData) throws NoSuchAlgorithmException {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		// 转换utf-8
		byte[] btInput = null;
		try {
			btInput = srcData.getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str).toUpperCase();

	}



//	public static void main(String[] args) throws Exception {
//
//		// 签名原文
//		String text = "appId=cloud001&channelShareId=SHARE0001&phone=13828936758&thirdCustId=13828936758&appSecret=10bc3a7b05c2477b9ffbaf334a271f78";
//
//		// 对原报文进行MD5提取摘要：
//		String md5Result = MD5(text);
//		System.out.println("MD5摘要: " + md5Result);// 记得转为大写
//
//		// 私钥签名
//		String sign = RSAEncode(privateString, md5Result);
//		System.out.println("sign:"+sign);
//		// 私钥签名后的数据
//		String rsaDecode = RSADecode(publicString, sign);
//		// 公钥解密
//		System.out.println("公钥解密: " + rsaDecode);
//	}

}