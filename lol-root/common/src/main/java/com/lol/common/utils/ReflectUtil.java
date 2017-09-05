package com.lol.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 
 * 反射工具类
 * @author yangli
 *
 */
public class ReflectUtil {
    private Object object;
    
    public ReflectUtil(){
        
    }
    
    public ReflectUtil(Object object){
        this.object = object;
    }
    
    public ReflectUtil(Class<?> clazz) throws InstantiationException, IllegalAccessException{
        this.object = clazz.newInstance();
    }
    
    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
                .isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }
    
    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }
    
    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * 
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {//NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }
    
    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter方法.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {

        }
        return result;
    }
    
    /**
     * 通过反射获取定义的字段
     * @return
     */
    public Field[] getFields(){
        if(null != object){
            return object.getClass().getDeclaredFields();
        }
        return null;
    }
    
    /**
     * 获取类定义的字段名
     * @return
     */
    public String[] getFieldsName(){
        Field[] fields = getFields();
        String[] result = null;
        
        if(null != fields){
            result = new String[fields.length];
            for(int i = 0; i < fields.length; ++i){
                result[i] = fields[i].getName();
            }
        }
        
        return result;
    }
    
    /**
     * 获取定义的方法
     * @param name 方法名
     * @return
     */
    public Method getDeclaredMethod(String name){
        Method result = null;
        try{
            Method[] methods = object.getClass().getDeclaredMethods();
            for(Method method : methods){
                if(name.equals(method.getName())){
                    result = method;
                    break;
                }
            }
        }catch(SecurityException e){
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Method getMethod(String name){
        Method result = null;
        try{
            Method[] methods = object.getClass().getMethods();
            for(Method method : methods){
                if(name.equals(method.getName())){
                    result = method;
                    break;
                }
            }
        }catch(SecurityException e){
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 获取属性值
     * @param m
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object getMethodValue(Method m) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if(null != m){
            Object value = m.invoke(object, new Object[0]);
            if(null != value){
               return value;
            }
        }
        return null;
    }
    
    /**
     * 获得超类的参数类型，取第一个参数类型
     * @param clazz
     * @return Class<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenericType(final Class<?> clazz) {
        return (Class<T>)(getClassGenericType(clazz, 0));
    }
    
    /**
     * 
     * 根据索引获得超类的参数类型
     * @param clazz
     * @param index
     * @return Class
     */
    @SuppressWarnings("rawtypes")
    public static Class  getClassGenericType (final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType))
            return Object.class;
        
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if((index < 0) || (index >= params.length))
            return Object.class;
        if(!(params[index] instanceof Class))
            return Object.class;
        
        return (Class)params[index];
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

