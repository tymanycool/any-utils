package com.tiany.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpUtilTest {
    @Test
    public void get() throws Exception {
        String ret = HttpUtil.get(true, "http://www.baidu.com");
        System.out.println(ret);
    }

    @Test
    public void post() throws Exception {
    }

    @Test
    public void createSSLInsecureClient() throws Exception {
    }

    @Test
    public void httpRequest() throws Exception {
    }

    @Test
    public void getParam() throws Exception {
    }

    @Test
    public void http() throws Exception {
    }

}