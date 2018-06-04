package com.tiany.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

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

}