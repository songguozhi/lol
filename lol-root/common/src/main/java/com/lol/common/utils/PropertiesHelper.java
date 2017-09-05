package com.lol.common.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 
 * describing: 属性文件工具类，获取key、value属性 
 * @author yangli
 *
 */

public class PropertiesHelper {
    
    private static final Logger LOGGER = Logger.getLogger(PropertiesHelper.class);

    /**
     * 加载类路径一个资源或所有匹配的资源
     * @param path 
     * @return
     */
    public static Properties getProperties(String path){
        Properties properties = new Properties();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
            resources = resolver.getResources(path);
            for(int i = 0; i< resources.length; ++i){
                properties.load(resources[i].getInputStream());
            }
           
        } catch (IOException e) {
            LOGGER.error("class:[PropertiesHelper] methord:[getProperties] exception: " + e.getMessage());
        }

        return  properties;
    }
    

}
