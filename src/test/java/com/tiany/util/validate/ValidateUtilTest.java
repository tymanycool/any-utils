package com.tiany.util.validate;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateUtilTest {
    @Test
    public void validateByRegex() throws Exception {
        boolean b = ValidateUtil.validateByRegex("15123071840", ValidateUtil.REG_PHONE);
        System.out.println(b);
    }

}