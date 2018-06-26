package com.tiany.util.validate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ValidateUtilTest {
    @Test
    public void validateByRegex() throws Exception {
        boolean b = ValidateUtil.validateByRegex("15123071840", ValidateUtil.REG_PHONE);
        System.out.println(b);
    }

    @Test
    public void test(){
        HashMap<Object, Object> paramsMap = new HashMap<>();
        paramsMap.put("name","tianyao");
        paramsMap.put("age","");
        paramsMap.put("hobby","sport");
        ValidateWrapper validateWrapper = new ValidateWrapper("name", "姓名", paramsMap.get("name"));
        ValidateWrapper validateWrapper2 = new ValidateWrapper("age", "年龄", paramsMap.get("age"));
        ValidateWrapper validateWrapper3 = new ValidateWrapper("hobby", "爱好", paramsMap.get("hobby"),"1","2");

        ArrayList<ValidateWrapper> list = new ArrayList<>();
        list.add(validateWrapper);
        list.add(validateWrapper2);
        list.add(validateWrapper3);

        List<String> strings = ValidateUtil.validateMustAndEnums(list);
    }

    public void test2(){

    }
    public void test3(){

    }
}