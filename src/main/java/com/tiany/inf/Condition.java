package com.tiany.inf;

/**
 * 遍历条件接口
 */
public interface Condition {
    boolean matches(Object obj,Object nextObj);
}
