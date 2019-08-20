package com.tong.common.utils;

import org.dozer.Mapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DozerConvertUtil {

    private static Mapper dozerMapper;

    public static void setDozerMapper(Mapper dozerMapper) {
        DozerConvertUtil.dozerMapper = dozerMapper;
    }

    public static <T, S> T convert(S s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        return dozerMapper.map(s, clz);
    }

    public static <T, S> List<T> convert(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (S vs : s) {
            list.add(dozerMapper.map(vs, clz));
        }
        return list;
    }

    public static <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<T>();
        for (S vs : s) {
            set.add(dozerMapper.map(vs, clz));
        }
        return set;
    }

    public static <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = dozerMapper.map(s[i], clz);
        }
        return arr;
    }

}
