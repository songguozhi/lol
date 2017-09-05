package com.lol.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * describing：类工具类，判断类是否为原生类或包含类
 * @author yangli
 *
 */
public class ClassHelper {
    private static List<Class<?>> clazzList = null;
    static{
        clazzList = new ArrayList<Class<?>>();
        
        clazzList.add(String.class);
        clazzList.add(Integer.class);
        clazzList.add(Long.class);
        clazzList.add(Double.class);
        clazzList.add(BigDecimal.class);
        clazzList.add(Number.class);
        clazzList.add(Boolean.class);
        clazzList.add(java.util.Date.class);
        clazzList.add(java.sql.Date.class);
        clazzList.add(java.sql.Timestamp.class);
    }

    /**
     * 类的类型判断
     * @param clazz
     * @return
     */
    public final static boolean isPrimitive(Class<?> clazz) {
        
        return clazz.isPrimitive() || contains(clazz);
    }
    
    /**
     * 类是否被包含
     * @param clazz
     * @return
     */
    private final static boolean contains(Class<?> clazz){

        return clazzList.contains(clazz);
    }
}

