package com.lol.common.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.AbstractUUIDGenerator;
import org.hibernate.id.Configurable;
import org.hibernate.type.Type;

import com.lol.common.utils.UuidUtil;

/**
 * 自定义UUID主键生成策略
 * 当目标entity有主键Id时无论数据库是否存在都使用其自带的主键
 * @author yl
 *
 */
public class UuidStrategy extends AbstractUUIDGenerator implements Configurable{

	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		try {
			Method method = object.getClass().getMethod("getId");
			Serializable str;
			str = (Serializable) method.invoke(object);
			if(str!=null&&!StringUtils.isNotBlank(str.toString()))
				return str;
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return UuidUtil.uuid();
	}

	public void configure(Type arg0, Properties arg1, Dialect arg2)
			throws MappingException {
	}


}
