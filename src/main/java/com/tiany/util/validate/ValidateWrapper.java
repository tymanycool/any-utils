package com.tiany.util.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 封装校验数据
 * @author tianyao
 * @version 1.0
 */
public class ValidateWrapper {
    // 字段的key
    private String key;
    // 字段对应的中文显示名字
    private String displayName;
    // 字段的值
    private Object value;
    // 字段的的值必须在enums的范围内，如果为null则不做校验
    private List<Object> enums = new ArrayList<>();
    // 校验方式
    public static final String VALIDATE_TYPE_MUST = "must";
    public static final String VALIDATE_TYPE_LENGTH = "length";
    public static final String VALIDATE_TYPE_ENUMS = "enums";
    public static final String VALIDATE_TYPE_REGEX = "regex";
    /**
     * 字段的长度校验,支持如下格式
     * "11"或者"=11":表示长度等于11
     * ">11":表示长度大于11
     * "<11":表示长度小于11
     * ">=11":表示长度大于等于11
     * "<=11":表示长度小于等于11
     */
    private String length;
    private int len;
    // 通过正则表达式校验(正则表达式优先)
    private String regex;



    public ValidateWrapper() {
    }

    public ValidateWrapper(String key, String displayName, Object value){
        this.key = key;
        this.displayName = displayName;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<Object> getEnums() {
        return enums;
    }
    public ValidateWrapper addEnums(Object... enums){
        this.enums.addAll(Arrays.asList(enums));
        return this;
    }

    /**
     * 校验长度等于length
     * @param length
     * @return
     */
    public ValidateWrapper addLength(int length){
        this.length = String.valueOf(length);
        this.len = length;
        return this;
    }
    /**
     * 字段的长度校验,支持如下格式
     * "11"或者"=11":表示长度等于11
     * ">11":表示长度大于11
     * "<11":表示长度小于11
     * ">=11":表示长度大于等于11
     * "<=11":表示长度小于等于11
     */
    public ValidateWrapper addLength(String lenExp){
        this.length = lenExp;
        String num = "";
        for(int i = lenExp.length()-1;i >= 0;i--){
            if(Character.isDigit(lenExp.charAt(i))){
                num = lenExp.charAt(i)+num;
            }
        }
        this.len = Integer.valueOf(num);
        return this;
    }

    /**
     * 用正则表达式校验
     * @param regex
     * @return
     */
    public ValidateWrapper addRegex(String regex){
        this.regex = regex;
        return this;
    }

    /**
     * 得到校验的方式
     * @return
     */
    public String getValidateType(){
        // 优先校验正则
        if(regex != null&&!"".equals(regex)){
            return VALIDATE_TYPE_REGEX;
        }else if(length != null&&!"".equals(length)){
            return VALIDATE_TYPE_LENGTH;
        }else if (enums !=null && enums.size()!=0){
            return VALIDATE_TYPE_ENUMS;
        }
        return VALIDATE_TYPE_MUST;
    }

    public String getLength() {
        return length;
    }

    public int getLen() {
        return len;
    }
    public String getRegex() {
        return regex;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ValidateWrapper{");
        sb.append("key='").append(key).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append(", value=").append(value);
        sb.append(", enums=").append(enums);
        sb.append(", length='").append(length).append('\'');
        sb.append(", regex='").append(regex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
