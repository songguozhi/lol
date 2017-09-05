package com.lol.common.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.lol.common.builder.SqlBuilder;
import com.lol.common.dao.BaseDbSqlDao;
import com.lol.common.pojos.Page;
import com.lol.common.query.QueryConstants;
import com.lol.common.query.QueryParser;
import com.lol.common.utils.ClassHelper;
import com.lol.common.utils.HibernateConvertResultSetHandler;
import com.lol.common.utils.HibernateListConvertResultSetHandler;
import com.lol.common.utils.PropertiesHelper;
import com.lol.common.utils.ReflectUtil;


/**
 * <p>describing：用sql数据库查询dao接口实现类</p>
 * <p>version:0.0.1</p>
 * <p>time:2015-4-5 11:00</p>
 * 
 * @author pg
 * @version 0.0.1
 *
 */
@Repository
public class BaseDbSqlDaoImpl implements BaseDbSqlDao{
	private static final Logger LOGGER = Logger.getLogger(BaseDbSqlDaoImpl.class);
    private static String DATA_TYPE = null;
    public static final String DATA_MSSQL = "mssql";
    public static final String DATA_MYSQL = "mysql";
    public static final String DATA_ORACLE = "oracle";
    public static final String DATA_TYPE_PROPERTY = "classpath:datasource/datasource.properties";
    
    static{
        Properties properties = PropertiesHelper.getProperties(DATA_TYPE_PROPERTY);
        DATA_TYPE = properties.getProperty("jdbc.type");
    }

    public void BaseDbutilsDaoImpl(){
    }
    @Autowired
    private DataSource dataSource;
    
  //--------------------------------------辅助方法-----------------------------------------------------------
    /**
    * 获得SqlBuilder
    * @param queryName
    * @param paramMap
    * @return
    * @throws Exception 
    */
   private SqlBuilder getSqlBuilder(String queryName,Map<String,?> paramMap) throws Exception{

       String sql = QueryParser.getQueryString(queryName, paramMap);
       LOGGER.info("sql语句调试:\r\n"+sql);
       //变量名称List
       List<String> paramNameList = new ArrayList<String>();
       //变量值List
       List<Object> paramValueList = new ArrayList<Object>();
               
       //将sql中的":变量名"替换成?
       String fm ="(:)\\s*\\w+|(:\\$)\\s*\\w+";
       Pattern p = Pattern.compile(fm);
       Matcher matcher = p.matcher(sql);  
       while (matcher.find()) {
           paramNameList.add(matcher.group());
       }
       if(paramNameList.size()>0){
           for(String s : paramNameList){
        	   if(s.startsWith(":$")){
        		   String s1 =s;
        		   s = s.trim().replace(":$","").trim();
        		   @SuppressWarnings("unchecked")
				List<String> strs = (List<String>) paramMap.get(s);
        		   StringBuilder sb = new StringBuilder();
        		   sb.append("(");
        		   for(int i =0;i<strs.size();i++){
        			   if(i>0)
        				   sb.append(",");
        			   sb.append("?");
        		   }
        		   sb.append(")");
                   sql = sql.replace(s1,sb.toString());
                   paramValueList.addAll(strs);
        	   }else{
        		   sql = sql.replace(s,"?");
        		   s = s.trim().replace(":","").trim();
        		   if(paramMap.get(s)!=null && !"".equals((paramMap.get(s)+"").trim())){
        			   paramValueList.add(paramMap.get(s));
        		   }
        	   }
           }
       }

       SqlBuilder sqlBuilder = new SqlBuilder(sql);
       sqlBuilder.setParams(paramValueList);
       return sqlBuilder;
   }
    
    protected QueryRunner getQueryRunner() {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner;
    }
    
    //--------------------------------------辅助方法结束-----------------------------------------------------------
    
    public int update(SqlBuilder sqlBuilder) throws Exception {
        return getQueryRunner().update(DataSourceUtils.getConnection(dataSource),sqlBuilder.getQuerySql(), sqlBuilder.getValues());
    }

    public int update(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return update(getSqlBuilder(queryName, paramMap));
    }

    public int[] batchBySql(String sql, Object[][] params) throws Exception {
        return getQueryRunner().batch(DataSourceUtils.getConnection(dataSource),sql, params);
    }

    public int updateBySql(String sql,Object... params)throws Exception{
    	return getQueryRunner().update(DataSourceUtils.getConnection(dataSource),sql, params);
    }
    
    //----------------------------数量统计---------------------------------------
    public long findCount(SqlBuilder sqlBuilder) throws Exception {
        Number num = (Number) getQueryRunner().query(sqlBuilder.getTotalRecordsSql(),new ScalarHandler<Number>(), sqlBuilder.getValues());
        return (num != null) ? num.longValue() : -1;
    }

    public long findCount(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return findCount(getSqlBuilder(queryName, paramMap));
    }

    //---------------------------查询一条记录，封装到实体T
    
    public <T> T findOne(Class<T> beanClass, SqlBuilder sqlBuilder)
            throws Exception {
        String sql = sqlBuilder.getQuerySql();
        Object[] params = sqlBuilder.getValues();
        return (T) getQueryRunner().query(sql,ClassHelper.isPrimitive(beanClass)? new ScalarHandler<T>(): new HibernateConvertResultSetHandler<T>(beanClass), params);
    }

    public <T> T findOne(Class<T> beanClass, String queryName,
            Map<String, ?> paramMap) throws Exception {
        return findOne(beanClass,getSqlBuilder(queryName, paramMap));
    }
    
    @SuppressWarnings("unchecked")
	public  <T> T findOneBySql(Class<T> beanClass,String sql,Object...params ) throws Exception{
    	List<?> list = getQueryRunner().query(sql, ClassHelper.isPrimitive(beanClass)? new  ColumnListHandler<T>(): new HibernateListConvertResultSetHandler<T>(beanClass), params);
    	if(list==null||list.size()==0)
    		return null;
    	return (T) list.get(0);
    }
    //---------------------------查询多条记录，封装到List<T>
    public <T> List<T> findList(Class<T> beanClass, SqlBuilder sqlBuilder)
            throws Exception {
        String sql = sqlBuilder.getQuerySql();
        Object[] params = sqlBuilder.getValues();
        return (List<T>) getQueryRunner().query(sql,ClassHelper.isPrimitive(beanClass)? new  ColumnListHandler<T>(): new HibernateListConvertResultSetHandler<T>(beanClass), params);
    }

    public <T> List<T> findList(Class<T> beanClass, String queryName,
            Map<String, ?> paramMap) throws Exception {
        return findList(beanClass,getSqlBuilder(queryName, paramMap));
    }

    public <T> List<T> findListBySql(Class<T> beanClass,String sql,Object... params) throws Exception{
    	return (List<T>) getQueryRunner().query(sql, ClassHelper.isPrimitive(beanClass)? new  ColumnListHandler<T>(): new HibernateListConvertResultSetHandler<T>(beanClass), params);
    }
    //---------------------------执行查询，将结果集保存到Map
    @SuppressWarnings("unchecked")
	public <T> Map<?, ?> findMap(Class<T> beanClass,SqlBuilder sqlBuilder,
            String keyPropertyName,Object clazzOrPropertyName) throws Exception {
        String sql = sqlBuilder.getQuerySql();
        Object[] params = sqlBuilder.getValues();
        
        List<T> list = (List<T>) getQueryRunner().query(sql,ClassHelper.isPrimitive(beanClass) ? new  ColumnListHandler<T>(): new HibernateListConvertResultSetHandler<T>(beanClass), params);
        Map<Object,Object> map = new HashMap<Object,Object>();
        if(list!=null && list.size()>0){
            for(Object obj : list){
                if(clazzOrPropertyName==obj.getClass()){
                    map.put(ReflectUtil.getFieldValue(obj, keyPropertyName), obj);
                }else if(clazzOrPropertyName instanceof String){
                    map.put(ReflectUtil.getFieldValue(obj, keyPropertyName), ReflectUtil.getFieldValue(obj, clazzOrPropertyName+""));
                }
            }
            for(Object obj : list){
                map.put(ReflectUtil.getFieldValue(obj, keyPropertyName), (T) obj);
            }
        }
        return map;
    }
    public <T> Map<?, ?> findMap(Class<T> beanClass, String queryName,
            Map<String, ?> paramMap, String keyPropertyName,
            Object clazzOrPropertyName) throws Exception {
        return findMap(beanClass,getSqlBuilder(queryName, paramMap),keyPropertyName,clazzOrPropertyName);
    }
    
    public <T> Page findPage(Class<T> beanClass, SqlBuilder sqlBuilder,
            Page page) throws Exception {
        //查询总条数
        Long totalRecords = findCount(sqlBuilder);
        
        int currentPage = page.getPageNum(), pageSize = page.getNumPerPage();
        if(totalRecords<=(currentPage-1)*pageSize&&currentPage>1){//判断分页后当前页是否有数据
        	currentPage--;
        	page.setPageNum(currentPage);
        }
        if((DATA_TYPE != null) && (DATA_TYPE.equals(DATA_MYSQL))){
            //mysql
            
            //limit 分页：第一个参数指定返回的第一行在所有数据中的位置，从0开始（注意不是1），第二个参数指定最多返回行 
            String pageSql =sqlBuilder.getQuerySql() +" LIMIT "+pageSize * (currentPage - 1)+","+pageSize;
            sqlBuilder.clear().append(pageSql);
        }else if((DATA_TYPE != null) && (DATA_TYPE.equals(DATA_MSSQL))){
          // msSql
            
          String sql = sqlBuilder.getQuerySql();
          StringBuilder sb = new StringBuilder();
          int fromIndex = sql.toLowerCase().indexOf("from");
          //"from" 前的select字串，不包括"from"
          String beforeFromSql = sql.substring(0, fromIndex);
          //"from"后面的select字串，包括"from"
          String afterFromSql = sql.substring(fromIndex);
          int orderbyIndex = afterFromSql.lastIndexOf("order by");
          String pageSql = "";
          String orderSql = " order by id";
          if(orderbyIndex > 0){
              pageSql =  afterFromSql.substring(0, orderbyIndex);
              orderSql = afterFromSql.substring(orderbyIndex);
          }
          else{
              pageSql = afterFromSql;
          }
          int groupbyIndex = afterFromSql.lastIndexOf("group by id");
          sb.append(beforeFromSql);
          sb.append(" from(select ROW_NUMBER() Over(");
          sb.append(orderSql);
          

          
          if(groupbyIndex < 0)
              sb.append(") as rownum,* ");
          else
              sb.append(") as rownum,id ");
          sb.append(pageSql);
          sb.append(") t where rownum between ");
          sb.append(((currentPage -1)* pageSize + 1) + " and ");
          sb.append(currentPage * pageSize + " ");
          sb.append(orderSql);
          sqlBuilder.clear().append(sb.toString());
          
          
          System.out.println(sb.toString()+".....................");
        }else if((DATA_TYPE != null) && (DATA_TYPE.equals(DATA_ORACLE))){
            //oracle
        }
      
        List<?> list = findList(beanClass,sqlBuilder);
        
        // 设置分页信息
        page.setList(list);
        page.setTotalCount(Integer.parseInt(totalRecords+""));
        return page;
    }
    
    public <T> Page findPage(Class<T> beanClass, SqlBuilder sqlBuilder,SqlBuilder countSqlBuilder,
            Page page) throws Exception {
        //查询总条数
//        Long totalRecords = findCount(sqlBuilder);
    	Long totalRecords = findCount(countSqlBuilder);
        int currentPage = page.getPageNum(), pageSize = page.getNumPerPage();
        if(totalRecords<=(currentPage-1)*pageSize&&currentPage>1){//判断分页后当前页是否有数据
        	currentPage--;
        	page.setPageNum(currentPage);
        }
        if((DATA_TYPE != null) && (DATA_TYPE.equals(DATA_MYSQL))){
            //mysql
            
            //limit 分页：第一个参数指定返回的第一行在所有数据中的位置，从0开始（注意不是1），第二个参数指定最多返回行 
            String pageSql =sqlBuilder.getQuerySql() +" LIMIT "+pageSize * (currentPage - 1)+","+pageSize;
            sqlBuilder.clear().append(pageSql);
        }else if((DATA_TYPE != null) && (DATA_TYPE.equals(DATA_MSSQL))){
          // msSql
            
          String sql = sqlBuilder.getQuerySql();
          StringBuilder sb = new StringBuilder();
          int fromIndex = sql.toLowerCase().indexOf("from");
          //"from" 前的select字串，不包括"from"
          String beforeFromSql = sql.substring(0, fromIndex);
          //"from"后面的select字串，包括"from"
          String afterFromSql = sql.substring(fromIndex);
          int orderbyIndex = afterFromSql.lastIndexOf("order by");
          String pageSql = "";
          String orderSql = " order by id";
          if(orderbyIndex > 0){
              pageSql =  afterFromSql.substring(0, orderbyIndex);
              orderSql = afterFromSql.substring(orderbyIndex);
          }
          else{
              pageSql = afterFromSql;
          }
          int groupbyIndex = afterFromSql.lastIndexOf("group by id");
          sb.append(beforeFromSql);
          sb.append(" from(select ROW_NUMBER() Over(");
          sb.append(orderSql);
          if(groupbyIndex < 0)
              sb.append(") as rownum,* ");
          else
              sb.append(") as rownum,id ");
          sb.append(pageSql);
          sb.append(") t where rownum between ");
          sb.append(((currentPage -1)* pageSize + 1) + " and ");
          sb.append(currentPage * pageSize + " ");
          sb.append(orderSql);
     
          sqlBuilder.clear().append(sb.toString());
        }else if((DATA_TYPE != null) && (DATA_TYPE.equals(DATA_ORACLE))){
            //oracle
        }
        
        List<?> list = findList(beanClass,sqlBuilder);
        
        // 设置分页信息
        page.setList(list);
        page.setTotalCount(Integer.parseInt(totalRecords+""));
        return page;
    }

    public <T> Page findPage(Class<T> beanClass, Page page, String queryName,
            Map<String, ?> paramMap) throws Exception {
        //使用临时map，是为了设置排序属性和排序类型
        Map<String,Object> mapTemp = new HashMap<String,Object>();
        if(paramMap!=null && paramMap.size()>0){
            for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
                 mapTemp.put(entry.getKey().toString(), entry.getValue());
            }
        }
        mapTemp.put(QueryConstants.ORDER_FIELD, page.getOrderField());
        mapTemp.put(QueryConstants.ORDER_DIRECTION, page.getOrderDirection());
        
        return findPage(beanClass, getSqlBuilder(queryName, mapTemp), page);
    }

	public <T> Page findPage(Class<T> beanClass, Page page, String queryName,
			String countQueryName, Map<String, ?> paramMap) throws Exception {
		 //使用临时map，是为了设置排序属性和排序类型
        Map<String,Object> mapTemp = new HashMap<String,Object>();
        if(paramMap!=null && paramMap.size()>0){
            for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
                 mapTemp.put(entry.getKey().toString(), entry.getValue());
            }
        }
        mapTemp.put(QueryConstants.ORDER_FIELD, page.getOrderField());
        mapTemp.put(QueryConstants.ORDER_DIRECTION, page.getOrderDirection());
		return findPage(beanClass, getSqlBuilder(queryName, mapTemp),getSqlBuilder(countQueryName, paramMap), page);
	}

}

