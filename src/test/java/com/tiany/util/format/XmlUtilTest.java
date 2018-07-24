package com.tiany.util.format;

import com.tiany.util.format.xml.Header;
import com.tiany.util.format.xml.Request;
import com.tiany.util.format.xml.User;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class XmlUtilTest {
    @Test
    public void bean2Xml() throws Exception {
        Request request = new Request();
        request.getHeader().setReqId("reqid_test");
        request.getHeader().setSign("token");
        request.getBody().setName("tianyao");
        request.getBody().setContent("business params");
        request.setPid("pid-test");

        User user = new User();
        user.setName("ttttttt");
        user.setAge(45);
        request.getBody().setUser(user);

        Header header = new Header();
        header.setReqId("tian_id");
        header.setSign("sign");
        request.getListHeader().add(header);

        header = new Header();
        header.setReqId("tian_id2");
        header.setSign("sign2");
        request.getListHeader().add(header);
        String s = XmlUtil.bean2Xml(request);
        String s2 = XmlUtil.obj2Xml(request);
        System.out.println(s);
        System.out.println(s2);
    }

    @Test
    public void list2Xml() throws Exception {
        List<Header> objects = new ArrayList<>();
        Header header = new Header();
        header.setReqId("tian_id");
        header.setSign("sign");
        objects.add(header);


        header = new Header();
        header.setReqId("tian_id2");
        header.setSign("sign2");
        objects.add(header);

        ArrayList<Object> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        list.add("test5");

        //String test = XmlUtil.list2Xml(objects, "test");
        String test = XmlUtil.list2Xml(objects,"test");
        String test2 = XmlUtil.bean2Xml(objects);
        System.out.println(test);
        System.out.println(test2);
    }

    @Test
    public void obj2Xml2() throws Exception {
        Request request2 = new Request();
        request2.getHeader().setReqId("reqid_test");
        request2.getHeader().setSign("token");
        request2.getBody().setName("tianyao");
        request2.getBody().setContent("business params");
        String s = XmlUtil.obj2Xml2(request2);
        System.out.println(s);
    }
    @Test
    public void obj2Xml() throws Exception {
        Request request2 = new Request();
        request2.getHeader().setReqId("reqid_test");
        request2.getHeader().setSign("token");
        request2.getBody().setName("tianyao");
        request2.getBody().setContent("business params");
        String s = XmlUtil.obj2Xml(request2);
        System.out.println(s);
    }
}