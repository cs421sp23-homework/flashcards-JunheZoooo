package com.example.windowscardmanage.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * 工具类  方法可重构，切不可随意改动
 */
public class ObjectUtils {

    /**
     * 字符串是否为空
     * @param s
     * @return
     */
    public static boolean isEmpty(String s){
        return s==null||s.length()==0?true:false;
    }

    /**
     * 字符串是否有值
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s){
        return s!=null&&s.length()!=0?true:false;
    }

    /**
     * 对象是否为空
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o){
        return o==null?true:false;
    }

    /**
     * 对象是否有值
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o){
        return o!=null?true:false;
    }

    /**
     * 文件是否为空
     * @param m
     * @return
     */
    public static boolean isEmpty(MultipartFile m){
        return m==null&&m.isEmpty();
    }

    /**
     * 文件是否为空
     * @param m
     * @return
     */
    public static boolean isNotEmpty(MultipartFile m){
        return m!=null&&!m.isEmpty();
    }
}
