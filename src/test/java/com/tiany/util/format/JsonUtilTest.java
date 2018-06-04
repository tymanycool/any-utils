package com.tiany.util.format;

import org.junit.Test;

import java.util.HashMap;

public class JsonUtilTest {
    @Test
    public void mapToJson() throws Exception {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("username","tianyao");
        map.put("age", 23);

        HashMap<Object, Object> map2 = new HashMap<>();
        map2.put("username","tianyao");
        map2.put("age", 23);
        map.put("map2",map2);

        String s = JsonUtil.obj2Json(map);
        System.out.println(s);
    }

}