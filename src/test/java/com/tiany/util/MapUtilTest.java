package com.tiany.util;

import com.tiany.bean.Person;
import com.tiany.bean.Son;
import com.tiany.util.format.xml.Header;
import com.tiany.util.format.xml.Request;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

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
        Map<String, ? extends Object> stringStringMap = MapUtil.bean2Map(request);
        List listHeader =  (List)stringObjectMap.get("listHeader");
    }

    @Test
    public void test() throws Exception {
        List<String> list = new ArrayList<>();
        method1(list);
    }

     public void method1( List<? extends Object> list){

    }

    @Test
    public void sort() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("a","aaaa");
        map.put("b","bb");
        map.put("d","aabbaa");
        map.put("c","bbb");
        map.put("s","aaddddaa");
        map.put("s2","aaddddaa");
        map.put("s1","aaddddaa");
        map =MapUtil.sort(map,false);
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }

    @Test
    public void testGetObject() throws Exception {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("tianyao","tianyaovalue");
        map.put("tianyao2","tianyaovalue");
        map.put("tianyao3","tianyaovalue");
        HashMap<Object, Object> map2 = new HashMap<>();
        map2.put("name","ti");
        map2.put("name1","ti1");
        map2.put("name2","ti2");
        map2.put("name3","ti3");
        map2.put("name4","ti4");
        map.put("data",map2);

        Object result = MapUtil.getObject(map, "data");
        System.out.println(result);
    }

    @Ignore
    @Test
    public void testMapToBean() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> sonMap = new HashMap<>();
        map.put("name","tianyao");
        map.put("age",23);
        map.put("birthday","1992-12-21");
        //map.put("lastName","son...");
        map.put("son",sonMap);
        sonMap.put("lastName","son...");
        Person person = new Person();
        Son son = new Son();
        person.setSon(son);
        person = MapUtil.mapToBean(map, person);
        System.out.println(person);

    }
}