package com.lol.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;

import org.apache.log4j.Logger;

/**
 * 
 * <p>describing:对象拷贝工具类，例如：
 * mapperFactory.classMap(PersonSource.class, PersonDestination.class)
 * .field("firstName", "givenName")
 * .field("lastName", "sirName")
 * .byDefault()
 * .register();
 * 
 * MapperFacade mapper = mapperFactory.getMapperFacade();
 * PersonDest destination = mapper.map(source, PersonDest.class);
 * also: map(objectA, objectB) 
 * </p>
 * @author yangli
 *
 */
public class OrikaMappingUtil {
public static final String DISABLE_DEBUG_MODE = "ma.glasnost.orika.test.MappingUtil.noDebug";
    
    private static final Logger LOGGER = Logger.getLogger(OrikaMappingUtil.class);
    
    /**
     * 创建一个默认的MapperFactory实例
     * 
     * @return 
     */
    public static MapperFactory getMapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }
    
    /**
     * 已调试模式启动一个MapperFactory实例
     * @param debugMode 是否使用调试模式
     * @return
     */
    public static MapperFactory getMapperFactory(boolean debugMode) {
        if (debugMode) {
            if (Boolean.valueOf(System.getProperty(DISABLE_DEBUG_MODE))) {
                LOGGER.warn("Debug mode was requested via MappingUtil " +
                        "when it was explicitly disabled");
                return getMapperFactory();
            } else {
                return new DefaultMapperFactory.Builder().compilerStrategy(
                        new EclipseJdtCompilerStrategy()).build();
            }
        } else {
            return getMapperFactory();
        }
    }
    
    /**
     * 默认映射对象，默认拷贝null字段
     * @param sourceClass 源对象类型
     * @param distClass 目标对象类型
     * @return
     */
    public static MapperFacade getDefMapperFacade(Class<?> sourceClass, 
            Class<?> distClass){
       
        return getDefMapperFacade(sourceClass, distClass, true);
    }
    
    /**
     * 默认映射对象，是否拷贝null字段
     * @param sourceClass 源对象类型
     * @param distClass 目标对象类型
     * @param isMappingNull
     * @return
     */
    public static MapperFacade getDefMapperFacade(Class<?> sourceClass, 
            Class<?> distClass, boolean isMappingNull){
        MapperFactory mapperFactory = getMapperFactory();
        if(isMappingNull){
            mapperFactory.classMap(sourceClass, distClass).byDefault().register();
        }else{
            mapperFactory.classMap(sourceClass, distClass)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault().register();
        }
        
        return mapperFactory.getMapperFacade();   
    }
}
