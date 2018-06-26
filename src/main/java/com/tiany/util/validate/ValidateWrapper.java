package com.tiany.util.validate;

/**
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
    private Object[] enums;

    ValidateWrapper(String key,String displayName,Object value,Object... enums){
        this.key = key;
        this.displayName = displayName;
        this.value = value;
        this.enums = enums;
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

    public Object[] getEnums() {
        return enums;
    }

    public void setEnums(Object[] enums) {
        this.enums = enums;
    }
}
