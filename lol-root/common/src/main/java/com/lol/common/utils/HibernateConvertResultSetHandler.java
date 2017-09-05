package com.lol.common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BaseResultSetHandler;


/**
 * 用于扩展dbutils，如果待封装对象字段中包含hibernate注解优先根据注解中的字段名进行注入。
 * @author yangli
 *
 */
public class HibernateConvertResultSetHandler<T> extends BaseResultSetHandler<T>{

	private final Class<T> type;
	
	public HibernateConvertResultSetHandler(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	protected T handle() throws SQLException {
		ResultSet rs = getAdaptedResultSet();
		if(!rs.next())
			return null;
		DbutilsConvert<T> convert = new DbutilsConvert<T>(type, rs);
		return convert.handle();
	}

}
