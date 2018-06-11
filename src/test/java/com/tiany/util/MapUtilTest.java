package com.tiany.util;

import com.tiany.util.format.xml.Header;
import com.tiany.util.format.xml.Request;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class MapUtilTest {
    @Test
    public void xml2Map() throws Exception {
        String test = "";
        test += "<html>";
        test += "<header>             \r\n";
        test += "\t\t<meta>test</meta>";
        test += "<meta>test1</meta>";
        test += "<meta2>test2</meta2>";
        test += "</header>";
        test += "<body>";
        test += "</body>";
        test += "<meta3>test3</meta3>";
        test += "</html>";

        Map<String, Object> stringObjectMap = MapUtil.xml2Map(test);
        System.out.println(stringObjectMap);
    }

    @Test
    public void bean2Map() throws Exception {
        Request request = new Request();
        request.getHeader().setReqId("reqid_test");
        request.getHeader().setSign("token");
        request.getBody().setName("tianyao");
        request.getBody().setContent("business params");

        Header header = new Header();
        header.setReqId("tian_id");
        header.setSign("sign");
        request.getListHeader().add(header);

        header = new Header();
        header.setReqId("tian_id2");
        header.setSign("sign2");
        request.getListHeader().add(header);

        request.setId(2);
        request.getHeader().setId(3);
        request.setPid("pid");

        Map<String, Object> stringObjectMap = MapUtil.obj2Map(request);
        Map<String, String> stringStringMap = MapUtil.bean2Map(request);
        List listHeader =  (List)stringObjectMap.get("listHeader");
    }
}