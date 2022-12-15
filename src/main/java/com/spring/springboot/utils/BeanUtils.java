package com.spring.springboot.utils;

public interface BeanUtils {
    @FunctionalInterface
    public interface BeanCopyUtilCallBack <S, T> {

        /**
         * 定义默认回调方法
         * @param t
         * @param s
         */
        void callBack(S t, T s);
    }
}
