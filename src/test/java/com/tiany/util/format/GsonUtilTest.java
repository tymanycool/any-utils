package com.tiany.util.format;

import com.tiany.util.format.xml.Header;
import com.tiany.util.format.xml.Request;
import com.tiany.util.format.xml.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class GsonUtilTest {
    @Test
    public void toJsonStr() throws Exception {
    }

    @Test
    public void fromJsonStr() throws Exception {
    }

    @Test
    public void toJsonStrWithDate() throws Exception {
        Request request = new Request();
        request.getHeader().setReqId("reqid_test");
        request.getHeader().setSign("token");
        request.getBody().setName("tianyao");
        request.getBody().setContent("business params");
        request.setPid("pid-test");

        com.tiany.util.format.xml.User user = new User();
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

        String s = GsonUtil.toJsonStrWithDate(header);
        System.out.println(s);
    }

    @Test
    public void fromJsonStrWithDate() throws Exception {
    }

}