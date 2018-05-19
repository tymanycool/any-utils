package com.tiany.util.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

public class DESUtilTest {
    @Test
    public void encrypt() throws Exception {
        String encrypt = DESUtil.encrypt("tianyao", "key");
        String key = DESUtil.decrypt(encrypt, "key");
        System.out.println(key);
    }

    @Test
    public void encrypt1() throws Exception {
    }

    @Test
    public void decrypt() throws Exception {
    }

    @Test
    public void decrypt1() throws Exception {
    }

}