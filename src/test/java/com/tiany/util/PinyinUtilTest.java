package com.tiany.util;

import static org.junit.Assert.*;

public class PinyinUtilTest {
    public static void main(String[] args){
        System.out.println(PinyinUtil.spell("张三", false,true));//缩写 zs
        System.out.println(PinyinUtil.spell("张三", false,false));//全拼小写 zhangsan
        System.out.println(PinyinUtil.spell("张三", true,false));//全拼大写 ZHANGSAN
    }

}