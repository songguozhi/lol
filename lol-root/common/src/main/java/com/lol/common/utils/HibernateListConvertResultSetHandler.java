package com.lol.common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BaseResultSetHandler;

/**
 * 用于扩展dbutils，如果待封装对象字段中包含hibernate注解优先根据注解中的字段名进行注入。
 * @author yangli
 *
 * @param <T>
 */
public class HibernateListConvertResultSetHandler<T> extends BaseResultSetHandler<List<T>>{

	private Class<T> type;
	public HibernateListConvertResultSetHandler(Class<T> type) {
		super();
		this.type = type;
	}
	
	@Override
	protected List<T> handle() throws SQLException {
		List<T> results = new ArrayList<T>();
		ResultSet rs = getAdaptedResultSet();
		if (!rs.next()) {
            return results;
        }
		DbutilsConvert<T> convert = new DbutilsConvert<T>(type, rs);
		do{
			results.add(convert.handle());
		}while(rs.next());
		return results;
	}

}
