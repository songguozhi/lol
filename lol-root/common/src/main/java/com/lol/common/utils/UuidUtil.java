package com.lol.common.utils;

import java.util.UUID;

/**
 * <p>describing: UUID构建器
 * </p>
 * @author yangli
 * @version 0.0.1
 *
 */

public class UuidUtil {
    /**
     * 返回没有连接字符“-”的uuid
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 返回带有连接字符“-”的uuid
     * @return
     */
    public static String uuidLinkerLine(){
        return UUID.randomUUID().toString();
    }
    
    public static void main(String[] arg){
        String uuid = uuidLinkerLine();
        
        System.out.println("UUID :" + uuid.replaceAll("-", ""));
        System.out.println("UUID linker line:" + uuid);
    }

}
