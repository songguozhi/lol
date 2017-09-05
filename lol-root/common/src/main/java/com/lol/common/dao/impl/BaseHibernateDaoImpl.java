package com.lol.common.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.common.builder.HqlBuilder;
import com.lol.common.dao.BaseHibernateDao;
import com.lol.common.pojos.Page;
import com.lol.common.query.QueryConstants;
import com.lol.common.query.QueryParser;

/**
 * 
 * <p>describing：DAO层基类实现</p>
 * <p>version:0.0.1</p>
 * <p>time:2015-4-2 14:46</p>
 * @author pg
 * @version 0.0.1
 * @param <T> 实体类型
 */

@Repository
public class BaseHibernateDaoImpl<T extends Serializable>
    implements BaseHibernateDao<T>{
    
    private static final Logger LOGGER = Logger.getLogger(BaseHibernateDaoImpl.class);
    
    @Autowired
    private SessionFactory sessionFactory;
  //-------------------------辅助方法开始-------------------------------------------------------
    
    public Session getSession(){
    	return sessionFactory.getCurrentSession();
    }
    
    /**
     * 通过Hql构建器创建query
     * @param hql 查询语句
     * @param hqlBuilder hql创建器
     * @return query对象
     */
    private Query getQueryByHqlQuery(String hql, HqlBuilder hqlBuilder){
        Query query = getSession().createQuery(hql);
        // 启用查询缓存，在第一次执行list时，把查询结果集放入缓存；
        // 第二次执行list时，先遍历查询缓存，如果没有找到，就去数据库查询
        query.setCacheable(true);
        hqlBuilder.setParamsToQuery(query);
        return query;
    }
    
    /**
     * 根据外部查询hql及参数，生成HqlBuilder
     * @param queryName 查询名
     * @param paramMap 查询参数
     * @return
     * @throws Exception
     */
    private HqlBuilder getHqlBuilder(String queryName, Map<String, ?> paramMap) throws Exception{
        String hql = QueryParser.getQueryString(queryName, paramMap);
        HqlBuilder hqlBuilder = new HqlBuilder(hql);
        hqlBuilder.setParamMap(paramMap);
        return hqlBuilder;
    }
    
    
    //-------------------------辅助方法结束-------------------------------------------------------
    /**
     * 强制与数据库同步
     */
    public void flush(){
        getSession().flush();
    }

    /**
     * 清除缓存数据
     */
    public void clear(){ 
        getSession().clear();
    }
    
    public T save(T entity) throws Exception {
        getSession().save(entity);
        return entity;
    }

    public T update(T entity) throws Exception {
        getSession().update(entity);
        return entity;
    }

    public T saveOrUpdate(T entity) throws Exception {
        getSession().saveOrUpdate(getSession().merge(entity));
        return entity;
    }

    public T delete(T entity) throws Exception {
        getSession().delete(entity);
        return entity;
    }

    /**
     * get在数据库中未找到，返回null
     */
    @SuppressWarnings("unchecked")
    public T get(Class<T> T, Serializable id) throws Exception {
        return (T)getSession().get(T, id);
    }

    //-------------------------------数据分页方法开始----------------------------------------------------
    public Page findPage(Page page, HqlBuilder hqlBuilder) throws Exception {
        int currentPage = page.getPageNum();
        int numPerPage = page.getNumPerPage();
        LOGGER.info("hql string: " + hqlBuilder.getQueryHqlStr());
        Query query = getQueryByHqlQuery(hqlBuilder.getQueryHqlStr(),hqlBuilder);
        query.setFirstResult(numPerPage * (currentPage - 1));
        query.setMaxResults(numPerPage);
        // 获取记录总条数
        int totalRecords = findCount(hqlBuilder);
        if(totalRecords<=(currentPage-1)*numPerPage&&currentPage>1){//判断分页后当前页是否有数据
        	currentPage--;
        	page.setPageNum(currentPage);
        }
        // 设置分页信息
        page.setList(query.list());
        page.setTotalCount(totalRecords);
        
        return page;
    }
    
    public Page findPage(Page page, HqlBuilder hqlBuilder,HqlBuilder countHqlBuilder) throws Exception {
        int currentPage = page.getPageNum();
        int numPerPage = page.getNumPerPage();
        LOGGER.info("hql string: " + hqlBuilder.getQueryHqlStr());
        Query query = getQueryByHqlQuery(hqlBuilder.getQueryHqlStr(),hqlBuilder);
        query.setFirstResult(numPerPage * (currentPage - 1));
        query.setMaxResults(numPerPage);
        // 获取记录总条数
        int totalRecords = findCount(countHqlBuilder);
        if(totalRecords<=(currentPage-1)*numPerPage&&currentPage>1){//判断分页后当前页是否有数据
        	currentPage--;
        	page.setPageNum(currentPage);
        }
        // 设置分页信息
        page.setList(query.list());
        page.setTotalCount(totalRecords);
        
        return page;
    }

    public Page findPage(Page page, String queryName, Map<String, ?> paramMap)
            throws Exception {
        // 使用临时map，是为了设置排序字段和排序方式
        Map<String, Object> mapTemp = new HashMap<String, Object>();
        if(null != paramMap)
        mapTemp.putAll(paramMap);
        // 插入排序信息
        mapTemp.put(QueryConstants.ORDER_FIELD, page.getOrderField());
        mapTemp.put(QueryConstants.ORDER_DIRECTION, page.getOrderDirection());
        
        return findPage(page, getHqlBuilder(queryName, mapTemp));
    }
    
    public Page findPage(Page page, String queryName,String countQueryName, Map<String, ?> paramMap)
            throws Exception {
        // 使用临时map，是为了设置排序字段和排序方式
        Map<String, Object> mapTemp = new HashMap<String, Object>();
        if(null != paramMap)
        mapTemp.putAll(paramMap);
        // 插入排序信息
        mapTemp.put(QueryConstants.ORDER_FIELD, page.getOrderField());
        mapTemp.put(QueryConstants.ORDER_DIRECTION, page.getOrderDirection());
        
        return findPage(page, getHqlBuilder(queryName, mapTemp),getHqlBuilder(countQueryName, paramMap));
    }
    //-------------------------------数据分页方法开始----------------------------------------------------

    //-------------------------------数量统计方法开始----------------------------------------------------
    public int findCount(HqlBuilder hqlBuilder) throws Exception {
        LOGGER.info("conut string : " + hqlBuilder.getCountHqlStr());
        Query query = getQueryByHqlQuery(hqlBuilder.getCountHqlStr(), hqlBuilder);
        Long size = (Long)query.uniqueResult();
        if(null != size)
            return size.intValue();
        return 0;
    }


    public int findCount(String queryName, Map<String, ?> paramMap)
            throws Exception {
        
        return findCount(getHqlBuilder(queryName, paramMap));
    }
    
    //-------------------------------数量统计方法结束----------------------------------------------------

    //-------------------------------批次更新或删除方法开始----------------------------------------------------
    public int batch(HqlBuilder hqlBuilder) throws Exception {
        Query query = getQueryByHqlQuery(hqlBuilder.getQueryHqlStr(),hqlBuilder);
        return query.executeUpdate();
    }

    public int batch(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return batch(getHqlBuilder(queryName, paramMap));
    }
  //-------------------------------批次更新或删除方法结束----------------------------------------------------

    //-------------------------------返回list列表对象开始----------------------------------------------------
    public List<?> findList(HqlBuilder hqlBuilder) throws Exception {
    	   LOGGER.info("hql string: " + hqlBuilder.getQueryHqlStr());
        return getQueryByHqlQuery(hqlBuilder.getQueryHqlStr(), hqlBuilder).list();

    }

    public List<?> findList(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return findList(getHqlBuilder(queryName, paramMap));
    }
    //-------------------------------返回list列表对象结束----------------------------------------------------

    //-------------------------------返回一个实体对象开始----------------------------------------------------
    @SuppressWarnings("unchecked")
    public T findOne(HqlBuilder hqlBuilder) throws Exception {
        List<?> list = findList(hqlBuilder);
        if((null != list) && !list.isEmpty())
            return (T)list.get(0);
        
        return null;
    }

    public T findOne(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return findOne(getHqlBuilder(queryName, paramMap));
    }
  //-------------------------------返回一个实体对象结束----------------------------------------------------

    public int findMax(HqlBuilder hqlBuilder) throws Exception {
        try {
            LOGGER.info("conut string : " + hqlBuilder.getQueryHqlStr());
            Query query = getQueryByHqlQuery(hqlBuilder.getQueryHqlStr(), hqlBuilder);
            Number size = (Number) query.uniqueResult();
            if(null != size)
                return size.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        
        return 0;
    }

    public int findMax(String queryName, Map<String, Object> paramMap) throws Exception {
        
        return findMax(getHqlBuilder(queryName, paramMap));
    }

	@SuppressWarnings("unchecked")
	public T merge(T t) throws Exception {
		return (T) getSession().merge(t);
	}

	@SuppressWarnings("unchecked")
	public T load(Class<T> T , Serializable id) throws Exception {
		return (T) getSession().load(T, id);
	}

	public void setReadOnly(Object entity, boolean readOnly) {
		getSession().setReadOnly(entity, readOnly);
	}

}
