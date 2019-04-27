package com.tiany.inf;

public interface Iter<T> {
    /**
     * 得到前一个对象的迭代器
     * @return
     */
    Iter<T> getPreIter();

    /**
     * 得到当前对象的值
     * @return
     */
    T getValue();

    /**
     * 得到回一个对象的迭代器
     * @return
     */
    Iter<T> getNextIter();
}
