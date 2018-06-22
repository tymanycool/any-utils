package com.tiany.util.io;

import org.junit.Test;

public class FileUtilTest {
    @Test
    public void writeClassPath() throws Exception {
        FileUtil.writeClassPath("com/tiany/test2.txt","content-test");
    }

    @Test
    public void readClassPath() throws Exception {
        System.out.println(FileUtil.readClassPath("com/tiany/test2.txt"));
    }

}