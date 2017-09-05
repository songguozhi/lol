package com.lol.common.query;

/**
 * <p>describing：查询常量值</p>
 * @author yangli
 * @version 0.0.1
 *
 */
public class QueryConstants {

    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "orderField";
    
    /**
     * 排序方向
     */
    public static final String ORDER_DIRECTION = "orderDirection";
    
    /**
     * 属性前缀，如：<#if  _query_propertyName??>，这时_query_propertyName
     * 不set进sql/hql语句中，仅仅作为判断的条件
     */
    public static final String QUERY_PREFIX = "_query_";
    
    /**
     * 根查询文件路径
     */
    public static final String QUERY_ROOT_FILE_PATH = "/query/query.xml";
    
    /**
     * 取到sql、hql模板的数据来源，xml或database；
     * 为xml时，从*.query.xml中获取；
     * 为database时，从数据库查询
     */
    public static String QUERY_SQL_HQL_FROM = "xml";
    
    /**
     * xml文件的编码
     */
    public static String ENCODE_UTF8 = "UTF-8";
    
    /**
     * 路径前缀
     */
    public static String PATH_PREFIX = "/";
    
    public static String YES= "YES";
    public static String NO="NO";
    public static String UNUSED_VALUE = "unused";
}
