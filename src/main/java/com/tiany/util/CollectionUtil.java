package com.tiany.util;

import com.tiany.impl.ListIter;
import com.tiany.inf.Condition;
import com.tiany.inf.Iter;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CollectionUtil {

//    public Object[] copy(Object[] src,Condition condition){
//
//        return null;
//    }

    /**
     * 数组arr中是否包含ch
     *
     * @param arr
     * @param ch
     * @return
     */
    public static boolean contains(char[] arr, char ch) {
        for (char c : arr) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }


    /**
     * list中是否包含str(忽略大小写)
     *
     * @param list
     * @param str
     * @return
     */
    public static boolean containsIgnoreCase(List<String> list, String str) {
        for (String s : list) {
            if (s.toUpperCase().equals(str.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * list中是否顺序包含words(忽略大小写)
     *
     * @param list
     * @param words
     * @return
     */
    public static boolean orderContainsIgnoreCase(List<String> list, String... words) {
        if (words == null || words.length == 0) {
            return false;
        }
        String source = "";
        String regex = "^.*";
        for (String s : list) {
            source += s.toUpperCase();
        }
        for (String s : words) {
            regex += s.toUpperCase() + ".*";
        }
        regex += "$";
        return source.matches(regex);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("create");
        list.add("create");
        list.add("tian3");
        list.add("table");
        list.add("and");
        list.add("and");
        list.add("tian2");
        list.add("tian3");

        String str = "an678set and me8888";

        String regex = "^.*set.*and.*me.*$";

        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        MatchResult matchResult = matcher.toMatchResult();
        boolean matches = str.matches(regex);

        int start = matchResult.start();



       // boolean b = orderContainsIgnoreCase(list, "create","create","and", "table2");


    }

    /**
     * 按照条件分割list
     *
     * @param list
     * @param condition
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(List<T> list, Condition<T> condition) {
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
        if (sublist.size() > 0) {
            ret.add(sublist);
        }
        return ret;
    }


    /**
     * 按照条件分割list
     *
     * @param list
     * @param condition
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitByIter(List<T> list, Condition<Iter<T>> condition) {
        List<List<T>> ret = new ArrayList<>();
        List<T> sublist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 == list.size()) {
                Iter iter1 = new ListIter(list, i);
                if (condition.matches(iter1, null)) {
                    ret.add(sublist);
                    sublist = new ArrayList<>();
                    continue;
                }
            } else {
                Iter iter1 = new ListIter(list, i);
                Iter iter2 = new ListIter(list, i + 1);
                if (condition.matches(iter1, iter2)) {
                    ret.add(sublist);
                    sublist = new ArrayList<>();
                    continue;
                }
            }
            sublist.add(list.get(i));
        }
        if (sublist.size() > 0) {
            ret.add(sublist);
        }
        return ret;
    }


    /**
     * 清除list中的空元素(包括null,空白字符组成的元素）
     *
     * @param list
     * @return
     */
    public static List<String> removeEmpty(List<String> list) {
        List<String> ret = new ArrayList<>();
        if (isEmpty(list)) {
            return ret;
        }
        for (String s : list) {
            if (StringUtil.isNotEmpty(s)) {
                ret.add(s);
            }
        }
        return ret;
    }

    /**
     * 以 conjunction 为分隔符将集合转换为字符串
     *
     * @param <T>         被处理的集合
     * @param collection  集合
     * @param conjunction 分隔符
     * @return 连接后的字符串
     */
    public static <T> String join(Iterable<T> collection, String conjunction) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (T item : collection) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(conjunction);
            }
            sb.append(item);
        }
        return sb.toString();
    }

    /**
     * 新建一个ArrayList
     *
     * @param <T>    参数对象
     * @param values 参数
     * @return ArrayList 对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        return new ArrayList<T>(Arrays.asList(values));
    }

    /**
     * 集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 集合是否不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * map是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * map是否不为空
     *
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
