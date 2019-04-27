package com.tiany.impl;

import com.tiany.inf.Iter;

import java.util.List;

public class ListIter<T> implements Iter<T> {
    private List<T> list;
    private int index;

    @Override
    public Iter getPreIter() {
        if (index - 1 < 0) {
            return null;
        }
        return new ListIter(list, index - 1);
    }

    @Override
    public T getValue() {
        T value = null;
        try {
            value = (T) list.get(index);
        } catch (Exception e) {
        }
        return value;
    }

    @Override
    public Iter getNextIter() {
        if (index + 1 >= list.size()) {
            return null;
        }
        return new ListIter(list, index + 1);
    }

    /**
     * @param list
     * @param index 当前位置
     */
    public ListIter(List<T> list, int index) {
        this.list = list;
        this.index = index;
    }
}
