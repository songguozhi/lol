package com.lol.web.common.utils;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * springmvc接口自动构建与包路径对应的返回路径
 * @author YangLi
 *
 */


public class UrlUitl {

	/**
	 * 构建与包路径对应的返回路径
	 * @param obj调用方法对应的类对象
	 * @return
	 */
	public static String getURL(Object obj){
		try{
		StackTraceElement[] temps=Thread.currentThread().getStackTrace();
		String methodName=temps[2].getMethodName();
		Method methods[]=obj.getClass().getDeclaredMethods();
		Method method =null;
		for(Method me:methods){
			if(me.getName().equals(methodName)){
				method=me;
				break;
			}
		}
		String[] childURL=method.getAnnotation(RequestMapping.class).value();
		RequestMapping annotation = obj.getClass().getAnnotation(RequestMapping.class);
		String[] baseURL = {""};
		if(annotation!=null)
    		baseURL=annotation.value();
		return baseURL[0]+childURL[0];
		}catch(Exception ex){
			Logger.getLogger(obj.getClass()).error("构建返回路径出错", ex);
			return "error";
		}	
	}
}
