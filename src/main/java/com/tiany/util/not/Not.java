package com.tiany.util.not;

import com.tiany.exception.BlankStringException;
import com.tiany.exception.EnptyStringException;
import com.tiany.exception.NullStringException;

public abstract class Not {
   public static void notNull(String str){
       if(str == null){
           throw new NullStringException("字符串不能为null,请检查");
       }
   }

    public static void notBlank(String str){
        if("".equals(str)){
            throw new BlankStringException("字符串不能为null,请检查");
        }
    }

    public static void notEnpty(String str){
        if(str == null || "".equals(str)){
            throw new EnptyStringException("字符串不能为null,请检查");
        }
    }

}
