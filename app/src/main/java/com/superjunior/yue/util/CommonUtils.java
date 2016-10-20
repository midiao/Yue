package com.superjunior.yue.util;

/**
 * Created by cb8695 on 2016/10/20.
 */

public class CommonUtils {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}
