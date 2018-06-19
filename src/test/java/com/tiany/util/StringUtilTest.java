package com.tiany.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void getCamelProperty() throws Exception {

        String test = "Last-name_tianyao";
        test = "name";
        String camelProperty = StringUtil.getCamelProperty(test);
        System.out.println(camelProperty);

    }

    @Test
    public void getCamelClassName() throws Exception {

        String test = "_last-name_tianyao";
        String camelProperty = StringUtil.getCamelClassName(test);
        System.out.println(camelProperty);

    }

    @Test
    public void escape() throws Exception {
        String escape = StringUtil.escape("田耀\"\t");
        System.out.println(escape);
        String str = "\u7530\u8000\"\t";
        System.out.println(StringUtil.unescape(str));

    }

    @Test
    public void testGetEncodeing() throws Exception {
        //String str = "5222田耀";
        String encoding = "gbk";
        // TODO 测试。。。
        String str = new String("tianyao??田耀，你的眼睛是那么的冰冷，我用手轻轻触摸，化成了冰，222".getBytes(encoding),encoding);
        System.out.println(str);
        String ret = StringUtil.getEncoding(str);
        System.out.println(ret);
    }

    @Test
    public void test() throws Exception {
        //new String
    }
}