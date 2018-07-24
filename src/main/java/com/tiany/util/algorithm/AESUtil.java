package com.tiany.util.algorithm;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES�㷨
 */
public class AESUtil {
    /**
     * ����
     *
     * @param content  ��Ҫ���ܵ�����
     * @param password ��������
     * @return
     */
    public static byte[] encrypt(byte[] content, String password) {
        try {
            //����key����һ����Կ
            SecretKeySpec key = new SecretKeySpec(generateKey(password), "AES");
            Cipher cipher = Cipher.getInstance("AES");// ����������
            byte[] byteContent = content;
            cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��
            byte[] result = cipher.doFinal(byteContent);
            return result; // ����
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
     * ����
     *
     * @param content  ����������
     * @param password ������Կ
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(generateKey(password), "AES");

            Cipher cipher = Cipher.getInstance("AES");// ����������
            cipher.init(Cipher.DECRYPT_MODE, key);// ��ʼ��
            byte[] result = cipher.doFinal(content);
            return result; // ����
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

    public static byte[] generateKey(String sPwd) {
        byte[] password = sPwd.getBytes();
        byte[] pwd = new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20,
                0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
        int len = password.length;
        if (len > 16) {
            len = 16;
        }
        System.arraycopy(password, 0, pwd, 0, len);
        return pwd;
    }

}
