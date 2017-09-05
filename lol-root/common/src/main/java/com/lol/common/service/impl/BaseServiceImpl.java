package com.lol.common.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lol.common.builder.HqlBuilder;
import com.lol.common.dao.BaseHibernateDao;
import com.lol.common.pojos.Page;
import com.lol.common.service.BaseService;
import com.lol.common.utils.ReflectUtil;

/**
 * 基础service实现
 * @author yangli
 *
 * @param <T>
 */
@Transactional
public class BaseServiceImpl<T extends Serializable> implements BaseService<T>{

	//实体类
    private Class<T> entityClass;
    //public static final String projectName = "/RealSample";
    public BaseServiceImpl(){
        entityClass = ReflectUtil.getClassGenericType(getClass());
    }
    
	@Autowired
    BaseHibernateDao<T> dao;
	
    public T save(T entity) throws Exception {
        return dao.save(entity);
    }

    public T update(T entity) throws Exception {
        return dao.update(entity);
    }

    public T saveOrUpdate(T entity) throws Exception {
        return dao.saveOrUpdate(entity);
    }

    public T delete(T entity) throws Exception {
        return dao.delete(entity);
    }

    public T get(Class<T> T, Serializable id) throws Exception {
        return dao.get(T, id);
    }

    public List<T> save(List<T> entitys) throws Exception {
        for(T entity : entitys)
            save(entity);
        return entitys;
    }

    public T delete(Class<T> T, Serializable id) throws Exception {
        T entity = dao.get(T, id);
        dao.delete(entity);
        return entity;
    }

    public T deleteById(Serializable id) throws Exception {
        T del = null;
        del = get(id);
        if(null != del)
            delete(del);
        return del;
    }

    public List<T> deleteByIds(List<Serializable> ids) throws Exception {
        List<T> dts = new ArrayList<T>();
        T del = null;
        if((null != ids) && (0 < ids.size())){
            for(Serializable id : ids){
                del = deleteById(id);
                if(null != del)
                    dts.add(del);
            }
        }
        return dts;
    }
    
    public List<T> delete(Collection<T> list)  throws Exception {
        List<T> dts = new ArrayList<T>();
        if((list != null) && (!list.isEmpty())){
            T del = null;
            for(T t : list){
                del = delete(t);
                if(null != del)
                    dts.add(del);
            }
        }
        return dts;
    }
    
    public T get(Serializable id) throws Exception {
        return dao.get(entityClass, id);
    }

    public int batch(HqlBuilder hqlBuilder) throws Exception {
        return dao.batch(hqlBuilder);
    }

    public int batch(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return dao.batch(queryName, paramMap);
    }

    public int findCount(HqlBuilder hqlBuilder) throws Exception {
        return dao.findCount(hqlBuilder);
    }

    public int findCount(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return dao.findCount(queryName, paramMap);
    }

    public List<?> findList(HqlBuilder hqlBuilder) throws Exception {
        return dao.findList(hqlBuilder);
    }

    public List<?> findList(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return dao.findList(queryName, paramMap);
    }

    public T findOne(HqlBuilder hqlBuilder) throws Exception {
        return dao.findOne(hqlBuilder);
    }

    public T findOne(String queryName, Map<String, ?> paramMap)
            throws Exception {
        return dao.findOne(queryName, paramMap);
    }

    public Page findPage(Page page, HqlBuilder hqlBuilder) throws Exception {
        return dao.findPage(page, hqlBuilder);
    }

    public Page findPage(Page page, String queryName, Map<String, ?> paramMap)
            throws Exception {
        return dao.findPage(page, queryName, paramMap);
    }

    public int findMax(HqlBuilder hqlBuilder) throws Exception {
       
        return dao.findMax(hqlBuilder);
    }

    public int findMax(String queryName, Map<String, Object> paramMap)
            throws Exception {
       
        return dao.findMax(queryName, paramMap);
    }

	public void clear() {
		dao.clear();
	}

	public T load(Class<T> T, Serializable id) throws Exception {
		return dao.load(T, id);
	}

	public T load(Serializable id) throws Exception {
		return dao.load(entityClass, id);
	}

	public Page findPage(Page page, String queryName, String countQueryName,
			Map<String, ?> paramMap) throws Exception {
		return dao.findPage(page, queryName, countQueryName, paramMap);
	}

	public void setReadOnly(Object entity, boolean readOnly) {
		dao.setReadOnly(entity, readOnly);
	}
}
