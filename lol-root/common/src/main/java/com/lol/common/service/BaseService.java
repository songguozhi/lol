package com.lol.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.lol.common.builder.HqlBuilder;
import com.lol.common.pojos.Page;

/**
 * 实体基于hibernate基本操作service
 * @author yangli
 *
 * @param <T>
 */
public interface BaseService<T extends Serializable> {

	 /**
     * 保存实体对象
     * @param entity 实体对象
     * @return 实体对象
     * @throws Exception
     */
    public T save(T entity) throws Exception;
    
    /**
     * 保存多个实体对象
     * @param entitys 实体对象集合
     * @return 实体对象
     * @throws Exception
     */
    public List<T> save(List<T> entitys) throws Exception;
    
    /**
     * 更新实体对象
     * @param entity 实体对象
     * @return 实体对象
     * @throws Exception
     */
    public T update(T entity) throws Exception;
    
    /**
     * 保存或更新实体对象
     * @param entity 实体对象
     * @return 实体对象
     * @throws Exception
     */
    public T saveOrUpdate(T entity) throws Exception;
    
    /**
     * 删除实体对象
     * @param entity 实体对象
     * @return 实体对象
     * @throws Exception
     */
    public T delete(T entity) throws Exception;
    
    /**
     * 根据id单个删除实体对象
     * @param id 实体对象id
     * @return 返回删除的实体对象
     * @throws Exception
     */
    public T deleteById(Serializable id) throws Exception;
    
    /**
     * 根据id批量删除实体对象
     * @param ids 实体对象id列表
     * @return 被删除的实体对象列表
     * @throws Exception
     */
    public List<T> deleteByIds(List<Serializable> ids) throws Exception;
    
    /**
     * 由主键获删除实体
     * @param T 实体Class
     * @param id 实体主键
     * @return 实体对象
     * @throws Exception
     */
    public T delete(Class<T> T , Serializable id) throws Exception;
    
    /**
     * 通过list对象删除多个实例
     * @param list 实体对象集合
     * @return
     * @throws Exception
     */
    public List<T> delete(Collection<T> list)  throws Exception;
    
    /**
     * 由主键获取实体
     * @param T 实体Class
     * @param id 实体主键
     * @return 实体对象
     * @throws Exception
     */
    public T get(Class<T> T , Serializable id) throws Exception;
    
    /**
     * 延迟加载对象
     * @param T
     * @param id
     * @return
     * @throws Exception
     */
    public T load(Class<T> T , Serializable id) throws Exception;
    
    /**
     * 延迟加载对象
     * @param T
     * @param id
     * @return
     * @throws Exception
     */
    public T load(Serializable id) throws Exception;
    
    /**
     * 根据主键id获取实体对象
     * @param id 实体对象id
     * @return 实体对象
     * @throws Exception
     */
    public T get(Serializable id) throws Exception;
    
    /**
     * HqlBuilder方式批量更新/批量删除
     * @param hqlBuilder hql构造器
     * @return 影响的行数
     * @throws Exception
     */
    public int batch(HqlBuilder hqlBuilder) throws Exception;
    
    /**
     * 外部查询语句批量更新/批量删除
     * @param queryName 外部查询名
     * @param paramMap 查询参数
     * @return 影响的行数
     * @throws Exception
     */
    public int batch(String queryName, Map<String, ?> paramMap) throws Exception;
    
    /**
     * hqlBuilder查询，记录总条数
     * @param hqlBuilder hql构建器
     * @return 记录总条数
     * @throws Exception
     */
    public int findCount(HqlBuilder hqlBuilder) throws Exception;
    
    /**
     * 外部hql查询，记录总条数
     * @param queryName 查询名
     * @param paramMap 查询参数
     * @return 记录总条数
     * @throws Exception
     */
    public int findCount(String queryName, Map<String, ?> paramMap) throws Exception;
    
    /**
     * Hqlbuilder查询，返回list对象列表
     * @param hqlBuilder hql构建器
     * @return list对象列表
     * @throws Exception
     */
    public List<?> findList(HqlBuilder hqlBuilder) throws Exception;
    
    /**
     * 外部查询语句返回list对象列表
     * @param queryName 查询名
     * @param paramMap 查询参数
     * @return list对象列表
     * @throws Exception
     */
    public List<?> findList(String queryName, Map<String, ?> paramMap) throws Exception;
    
    /**
     * 根据HqlBuilder返回一个实体
     * @param hqlBuilder hql语句构建器
     * @return 实体对象
     * @throws Exception
     */
    public T findOne(HqlBuilder hqlBuilder) throws Exception;
    
    /**
     * 根据外部查询语句返回一个实体
     * @param queryName 查询名
     * @param paramMap 查询参数
     * @return 实体对象
     * @throws Exception
     */
    public T findOne(String queryName, Map<String, ?> paramMap) throws Exception;
    
    /**
     * HqlBuilder分页查询，返回分页对象
     * @param page 当前分页对象
     * @param hqlBuilder hql构建器
     * @return 分页对象
     * @throws Exception
     */
    public Page findPage(Page page, HqlBuilder hqlBuilder) throws Exception;
    
    /**
     * 
     * @param page
     * @param queryName
     * @param countQueryName 查询总数用
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Page findPage(Page page,String queryName,String countQueryName,Map<String,?> paramMap)throws Exception;
    
    /**
     * 外部hql分页查询，返回分页对象
     * @param page 当前分页对象
     * @param queryName query查询名
     * @param paramMap 查询参数
     * @return 分页对象
     * @throws Exception
     */
    public Page findPage(Page page, String queryName, Map<String, ?>paramMap) throws Exception;
    
    /**
     * 查找最大值
     * @param hqlBuilder
     * @return
     * @throws Exception
     */
    public int findMax(HqlBuilder hqlBuilder) throws Exception;
    
    /**
     * 查找最大值
     * @param queryName
     * @param paramMap
     * @return
     * @throws Exception
     */
    public int findMax(String queryName, Map<String, Object> paramMap) throws Exception;

    /**
     * 清理缓存
     */
    public void clear();
    
    public void setReadOnly(Object entity,boolean readOnly);
}
