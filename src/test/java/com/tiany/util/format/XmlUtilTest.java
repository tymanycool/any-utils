package com.tiany.util.format;

import com.tiany.util.format.xml.Request;
import org.json.XML;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;



public class XmlUtilTest {
    @Test
    public void bean2Xml() throws Exception {
        Request request = new Request();
        request.getHeader().setReqId("reqid_test");
        request.getHeader().setSign("token");
        request.getBody().setName("tianyao");
        request.getBody().setContent("business params");
        String s = XmlUtil.bean2Xml(request);
        System.out.println(s);
    }

    @Test
    public void list2Xml() throws Exception {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("test1");
        objects.add("test2");
        objects.add("test3");
        objects.add("test4");

        String test = XmlUtil.list2Xml(objects, "test");
        System.out.println(test);
    }

    @Test
    public void obj2Xml() throws Exception {
        Request request = new Request();
        request.getHeader().setReqId("reqid_test");
        request.getHeader().setSign("token");
        request.getBody().setName("tianyao");
        request.getBody().setContent("business params");
        String s = XmlUtil.obj2Xml(request);
        System.out.println(s);
    }
}