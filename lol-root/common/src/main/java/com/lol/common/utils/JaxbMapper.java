package com.lol.common.utils;

import java.io.StringReader;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;

import org.springframework.util.Assert;

/**
 *  
 * <p>describing：copy from springside中的JaxbMapper
 * 使用Jaxb2.0实现XML<->Java Object的Mapper
 * 在创建时需要设定所有需要序列化的Root对象的Class
 * 特别支持Root对象是Collection的情形</p>
 * @author yangli
 *
 */
public class JaxbMapper {
    @SuppressWarnings("rawtypes")
    private static ConcurrentMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class, JAXBContext>();
    
    /**
     * 封装Root Element 是Collection的情况
     *
     */
    public static class CollectionWrapper{
        @XmlAnyElement
        protected Collection<?> collection;
    }
    
    //----------------------------------------------------辅助函数开始---------------------------------------------------------
   /**
    * 获取JAXBContext,从jaxbContexts中获取，如果没有就创建
    * @param clazz JAXBContexts的key
    * @return JAXBContext对象
    */
    @SuppressWarnings("rawtypes")
    protected static JAXBContext getJaxbContext(Class clazz){
        Assert.notNull(clazz, "'clazz' must not be null!");
        JAXBContext jaxbContext = jaxbContexts.get(clazz);
        if(null == jaxbContext){
            try {
                jaxbContext = JAXBContext.newInstance(clazz, CollectionWrapper.class);
                jaxbContexts.putIfAbsent(clazz, jaxbContext);
            } catch (JAXBException e) {
                //TODO  捕获异常
/*                throw new HttpMessageConversionException("Could not instantiate JAXBContext for " +
                        "class [" + clazz + "]:" + e.getMessage(), e);*/
            }
        }
        
        return jaxbContext;
    }
    //----------------------------------------------------辅助函数结束---------------------------------------------------------
    /**
     * 将xml转为java object
     * @param xml xml字符串
     * @param clazz 要转换成的object
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Class<T> clazz){
        try{
            StringReader reader = new StringReader(xml);
            return (T)createUnmarshaller(clazz).unmarshal(reader);
        }catch(JAXBException e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 创建Unmarshaller，线程不安全，需要每次创建或pooling。
     * @param clazz 创建Unmarshaller的类
     * @return Unmarshaller对象
     */
    public static Unmarshaller createUnmarshaller(Class<?> clazz){
        try{
            JAXBContext jaxbContext = getJaxbContext(clazz);
            return jaxbContext.createUnmarshaller();
        }catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
}

