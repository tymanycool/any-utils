package com.tiany.util;

import com.tiany.inf.Condition;

import java.util.ArrayList;
import java.util.List;

public abstract class CollectionUtil {
    /**
     * 数组arr中是否包含ch
     * @param arr
     * @param ch
     * @return
     */
    public static boolean contains(char[] arr,char ch){
        for(char c :arr){
            if (ch == c){
                return true;
            }
        }
        return false;
    }


    /**
     * list中是否包含str(忽略大小写)
     * @param list
     * @param str
     * @return
     */
    public static boolean containsIgnoreCase(List<String> list,String str){
        for(String s:list){
            if(s.toUpperCase().equals(str.toUpperCase())){
                return true;
            }
        }
        return false;
    }
    /**
     * 按照条件分割list
     *
     * @param list
     * @param condition
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(List<T> list, Condition condition) {
        List<List<T>> ret = new ArrayList<>();
        List<T> sublist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 == list.size()) {
                if (condition.matches(list.get(i), null)) {
                    ret.add(sublist);
                    sublist = new ArrayList<>();
                    continue;
                }
            } else {
                if (condition.matches(list.get(i), list.get(i + 1))) {
                    ret.add(sublist);
                    sublist = new ArrayList<>();
                    continue;
                }
            }
            sublist.add(list.get(i));
        }
        if(sublist.size()>0){
            ret.add(sublist);
        }
        return ret;

    }
}
