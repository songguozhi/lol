package com.lol.common.builder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.lol.common.query.QueryConstants;


/**
 * 
 * <p>describing：Hql构建器</p>
 * <p>version:0.0.1</p>
 * <p>time:2015-4-2 14:46</p>
 * 
 * @author pg
 * @version 0.0.1
 *
 */
public class HqlBuilder {

    private StringBuilder hqlStrBuild; // hql字符串buffer
    // Map参数名键值对，key:参数名称，value:参数值
    private Map<String, Object> paramMap;
    
    private Map<String, Object> getParamMap(){
        if(null == paramMap)
            paramMap = new HashMap<String, Object>();
        return paramMap;
    }
    
    public HqlBuilder(){
        hqlStrBuild = new StringBuilder("");
    }
    
    public HqlBuilder(String hql){
        hqlStrBuild = new StringBuilder(hql);
    }
    
    /**
     * 设置键、值对参数
     * @param name 键
     * @param value 值
     * @return 对象实体
     */
    public HqlBuilder setParam(String name, Object value){
        getParamMap().put(name, value);
        return this;
    }
    
    /**
     * 设置键，list参数
     * @param name 键
     * @param values list值列表
     * @return 对象实体
     */
    public HqlBuilder setParamList(String name, List<?> values){
        getParamMap().put(name, values);
        return this;
    }
    
    /**
     * 设置map参数键值
     * @param paramMap map键值对
     * @return 对象实体
     */
    public HqlBuilder setParamMap(Map<String, ?> paramMap){
        if(null != paramMap)
            getParamMap().putAll(paramMap);
        return this;
    }
    
    /**
     * 将builder中的参数设置到query中
     * @param query query对象
     * @return query对象
     */
    @SuppressWarnings("rawtypes")
    public Query setParamsToQuery(Query query){
        if((null != paramMap) && (paramMap.size() > 0)){
            for(Map.Entry<String, ?> entry : paramMap.entrySet()){
                Object value = entry.getValue();
                String key = entry.getKey();
                
                if((null != value) && (!"".equals((value + "").trim())) &&
                   (!"/".equals((value + "").trim())) &&
                   (!"%null%".equals(value + "")) && (!"%%".equals(value + "")) &&
                   (StringUtils.isNotBlank(key)) && (!QueryConstants.ORDER_FIELD.equals(key)) &&
                   (!QueryConstants.ORDER_DIRECTION.equals(key)) && 
                   (!QueryConstants.UNUSED_VALUE.equals(value))&&
                   (!key.startsWith(QueryConstants.QUERY_PREFIX)) ){
                    if(value instanceof Collection){
                        query.setParameterList(key, (Collection)value);
                    }else{
                        query.setParameter(key, value);
                    }
                }
            }
        }
        return query;
    }
    
    /**
     * 连接子hql语句
     * @param hql 子查询语句
     * @return HqlBuilder对象实体
     */
    public HqlBuilder append(String hql){
        hqlStrBuild.append(hql);
        return this;
    }
    
    /**
     * 获取hql语句字符串
     * @return hql字符串
     */
    public String getQueryHqlStr(){
        return hqlStrBuild.toString();
    }
    
    /**
     * 获取查询记录总数Hql字符串
     * @return Hql字符串
     */
    public String getCountHqlStr(){
        String hql = getQueryHqlStr();
        // "from"前的select子串
//        StringBuilder countHql = new StringBuilder("select count(*) ");
        String fm = "(distinct)\\s+\\S+";
		Pattern p = Pattern.compile(fm,Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(hql);
        int fromIndex = hql.toLowerCase().indexOf("from");
        String beforeFromSql = hql.substring(0, fromIndex);
        StringBuilder countHql = new StringBuilder();
        if(matcher.find()){
        	String str = matcher.group();
        	beforeFromSql = beforeFromSql.replace(str, " count("+str+") ");
        }else{
        	beforeFromSql = "select count(*) ";
        }
        countHql.append(beforeFromSql);
        // "from"后面的子串
        String afterFromHql = hql.substring(fromIndex);
        // 判断是否有order by
        int orderbyIndex = afterFromHql.toLowerCase().lastIndexOf("order by");
        if(orderbyIndex > 0)
            countHql.append(afterFromHql.substring(0, orderbyIndex));
        else
            countHql.append(afterFromHql);
        
        return countHql.toString();
    }
}

