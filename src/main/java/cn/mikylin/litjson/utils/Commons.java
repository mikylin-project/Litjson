package cn.mikylin.litjson.utils;

import java.util.*;

public final class Commons {

    public static <K,V> Map<K,V> newMap(int size) {
        return new HashMap<>(size);
    }

    public static <T> List<T> newArrayList(T... ts) {

        if (ts != null && ts.length != 0) {
            List<T> l = new ArrayList<>(ts.length + 8);
            Collections.addAll(l,ts);
            return l;
        }
        return new ArrayList<>(8);
    }

    public static Class checkNotNullOrMap(Class clz) {
        return clz == null ? Map.class : clz;
    }

    public static boolean isMap(Class clz) {
        return Map.class.isAssignableFrom(clz);
    }
}
