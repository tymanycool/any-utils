package com.tiany.util.format;

import org.junit.Test;

import java.util.HashMap;

class User{
    String lastName;
    int age;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class JsonUtilTest {
    @Test
    public void mapToJson() throws Exception {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("userName","tianyao");
        map.put("age", 23);

        HashMap<Object, Object> map2 = new HashMap<>();
        map2.put("username","tianyao");
        map2.put("age", 23);
        map.put("map2",map2);

        User user = new User();
        user.setAge(24);
        user.setLastName("TIANYAO");
        map.put("user",user);

        String s = JsonUtil.obj2Json(map);
        System.out.println(s);
    }

}