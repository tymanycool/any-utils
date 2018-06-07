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
}