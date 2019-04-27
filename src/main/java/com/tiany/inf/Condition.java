package com.tiany.inf;

/**
 * 遍历条件接口
 */
public interface Condition<T> {
    boolean matches(T obj,T nextObj);
}
