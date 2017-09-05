package com.lol.common.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lol.common.query.QueryConstants;


/**
 * 
 * <p>describing：sql构建器</p>
 * <p>version:0.0.1</p>
 * <p>time:2015-4-4 14:07</p>
 * 
 * @author pg
 * @version 0.0.1
 *
 */
public class SqlBuilder {
  //查询字符串
    private StringBuilder sqlStr;
    //list value
    private List<Object> values;
    
    public SqlBuilder(){
        sqlStr = new StringBuilder("");
    }
    
    public SqlBuilder(String sql) {
        sqlStr = new StringBuilder(sql);
    }
    
    public SqlBuilder clear() {
        sqlStr = new StringBuilder("");
        return this;
    }
    
    /**
     * 将参数值放入list
     * @return
     */
    private List<Object> putToList(Object value) {
        if (values == null) {
            values = new ArrayList<Object>();
        }
        if(value instanceof Collection<?>){
            values.addAll((Collection<?>) value);
        }else{
            values.add(value);
        }
        return values;
    }
    
    /**
     * 链接子sql语句
     */
    public SqlBuilder append(String sql) {
        sqlStr.append(sql);
        return this;
    }
    
    /**
     * sql语句字符串
     * @return
     */
    public String getQuerySql() {
        return sqlStr.toString();
    }
    
    /**
     * 查询记录总数字符串
     * @return
     */
    public String getTotalRecordsSql() {
        String sql = sqlStr.toString();
        int fromIndex = sql.toLowerCase().indexOf("from");
        //"from" 前的select字串，不包括"from"
//        int distinctIndex = sql.toLowerCase().indexOf("distinct");
        //是否存在"distinct",目前只过滤重复id字段
//        String beforeFromSql = "select count(1) ";
//        if(distinctIndex > 0)
//            beforeFromSql = "select count(distinct(id)) ";      //过滤重复id
        String fm = "(distinct)\\s+\\S+";
		Pattern p = Pattern.compile(fm,Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(sql);
		String beforeFromSql = sql.substring(0, fromIndex);
		if(matcher.find()){
			String str = matcher.group();
			beforeFromSql = beforeFromSql.replace(str, " count("+str+") ");
		}else{
			beforeFromSql = "select count(1) ";
		}
        //"from"后面的select字串，包括"from"，如果存在group by 则去除
        String afterFromSql = sql.substring(fromIndex);
        if(!(sql.split("select").length>2)){//存在子查询不适用
        	int groupbyIndex = sql.toLowerCase().lastIndexOf("group by");
        	if(groupbyIndex < 0)
        		afterFromSql = sql.substring(fromIndex);
        	else
        		afterFromSql = sql.substring(fromIndex, groupbyIndex);
        	int orderByIndex = afterFromSql.toLowerCase().lastIndexOf("order by");
        	if(orderByIndex > 0 )
        		afterFromSql = afterFromSql.substring(0, orderByIndex);
        }
        return beforeFromSql+ afterFromSql;
    }
    
    /**
     * 设置参数的值
     * @param param
     * @param value
     * @return
     */
    public SqlBuilder setParam(Object value) {
        putToList(value);
        return this;
    }
    
    public SqlBuilder setParams(List<Object> values) {
        putToList(values);
        return this;
    }
    
    /**
     * 设置Map名值对
     * @param paramMap
     */
    public void setParamMap(Map<String, ?> paramMap) {
        if(paramMap!=null && paramMap.size()>0){
            for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
                Object value = entry.getValue();
                String key = entry.getKey();
                if(value!=null && !"".equals((value+"").trim()) && 
                        (!"/".equals((value+"").trim())) &&
                        !"%null%".equals(value+"") && !"%%".equals(value+"")
                   && StringUtils.isNotBlank(key) 
                   && !QueryConstants.ORDER_FIELD.equals(key) && !QueryConstants.ORDER_DIRECTION.equals(key) && !key.startsWith(QueryConstants.QUERY_PREFIX)){
                    
                    putToList(value);
                }
            }
        }
    }
    
    /**
     * 获得参数值的的数组，便于作为dbutils.queryRunner.query的参数
     * @return
     */
    public Object[] getValues(){
        if(values!=null){
            return values.toArray();
        }
        return null;
    }
}
