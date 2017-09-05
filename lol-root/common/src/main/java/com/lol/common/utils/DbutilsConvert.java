package com.lol.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.StringUtils;

/**
 * 扩展dbutils功能，转换hibernate对象用转换器
 * @author yangli
 *
 * @param <T>
 */
public class DbutilsConvert<T> {

private final Class<T> type;
private final ResultSet rs;
	
	public DbutilsConvert(Class<T> type,ResultSet rs) {
		super();
		this.type = type;
		this.rs = rs;
		// 初始化需要封装对象中字段名与数据库字段名的映射关系
		if (!classColumnToProperty.containsKey(type)
				|| !classColumnToBeanProperty.containsKey(type)
				|| !classColumnToBean.containsKey(type)
				|| !classProps.containsKey(type)) {
			BeanInfo beanInfo = null;
			try {
				beanInfo = Introspector.getBeanInfo(type);
				props = beanInfo.getPropertyDescriptors();
				columnToProperty = new HashMap<String, PropertyDescriptor>();
				columnToBean = new HashMap<String, PropertyDescriptor>();
				columnToBeanProperty = new HashMap<String, PropertyDescriptor>();
				for(PropertyDescriptor pro:props){
					Method readMethod = pro.getReadMethod();
					Column column = readMethod.getAnnotation(Column.class);
					JoinColumn joinColumn = readMethod.getAnnotation(JoinColumn.class);
					if(column==null&&joinColumn==null){
						columnToProperty.put(pro.getName(), pro);
					}else{
						if(column!=null){
							if(StringUtils.isNotBlank(column.name()))
								columnToProperty.put(column.name(), pro);
							else
								columnToProperty.put(pro.getName(), pro);
						}else if(joinColumn!=null){
							if(StringUtils.isNotBlank(joinColumn.name())){
								Class<?> propertyType = pro.getPropertyType();
								PropertyDescriptor[] propertyDescriptors = null;
								if(classProps.containsKey(propertyType))
									propertyDescriptors = classProps.get(propertyType);
								else{
									BeanInfo beanInfo2 = Introspector.getBeanInfo(propertyType);
									propertyDescriptors = beanInfo2.getPropertyDescriptors();
								}
								if(StringUtils.isBlank(joinColumn.referencedColumnName())){
									for(PropertyDescriptor pr:propertyDescriptors){
										Method readm = pr.getReadMethod();
										if(readm.getAnnotation(Id.class)!=null){
											columnToBeanProperty.put(joinColumn.name(), pr);
											columnToBean.put(joinColumn.name(), pro);
											break;
										}
									}
								}else{
									for(PropertyDescriptor pr:propertyDescriptors){
										Method readm = pr.getReadMethod();
										Column column2 = readm.getAnnotation(Column.class);
										if(column2!=null&&column2.name().equalsIgnoreCase(joinColumn.referencedColumnName())){
											columnToBeanProperty.put(joinColumn.name(), pr);
											columnToBean.put(joinColumn.name(), pro);
											break;
										}
									}
								}
							}
						}
					}
				}
				classColumnToProperty.put(type, columnToProperty);
				classColumnToBean.put(type, columnToBean);
				classColumnToBeanProperty.put(type, columnToBeanProperty);
				classProps.put(type, props);
			} catch (Exception e) {
				e.printStackTrace();
				props = null;
			}
		}
		else{
			columnToProperty = classColumnToProperty.get(type);
			columnToBean = classColumnToBean.get(type);
			columnToBeanProperty = classColumnToBeanProperty.get(type);
			props = classProps.get(type);
		}
	}

	/**
	 * 如果需要注入的字段是一个基本类型且数据库查询出该字段为null将使用此map定义的值作为返回值
	 */
	private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();
	/**
	 * 类与columnToProperty的映射关系
	 */
	private static final Map<Class<?>,Map<String,PropertyDescriptor>> classColumnToProperty = new HashMap<Class<?>, Map<String,PropertyDescriptor>>();
	private static final Map<Class<?>,Map<String,PropertyDescriptor>> classColumnToBeanProperty = new HashMap<Class<?>, Map<String,PropertyDescriptor>>();
	private static final Map<Class<?>,Map<String,PropertyDescriptor>> classColumnToBean = new HashMap<Class<?>, Map<String,PropertyDescriptor>>();
	private static final Map<Class<?>,PropertyDescriptor[]> classProps = new HashMap<Class<?>, PropertyDescriptor[]>();
	/**
	 * 当前数据库字段名与泛型类中基本类型字段的属性编辑器的映射关系
	 */
	private Map<String,PropertyDescriptor> columnToProperty = null;
	/**
	 * 当前数据库字段名与泛型类中非基本类型字段（一对一，多对一的对应关系作为关系的维护方）的属性编辑器的映射关系
	 */
	private Map<String,PropertyDescriptor> columnToBeanProperty = null;
	/**
	 * 当前数据库字段名与非基本类型字段对象中基本类型字段的属性编辑器的映射关系
	 */
	private Map<String,PropertyDescriptor> columnToBean = null;
	private PropertyDescriptor[] props = null;
	static{
		primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
        primitiveDefaults.put(Short.TYPE, Short.valueOf((short) 0));
        primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte) 0));
        primitiveDefaults.put(Float.TYPE, Float.valueOf(0f));
        primitiveDefaults.put(Double.TYPE, Double.valueOf(0d));
        primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
        primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
        primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));
	}
	
	public T handle() throws SQLException {
		if(props==null||columnToProperty==null)
			throw new SQLException("DbutilsConvert Initialization error!");
		return createBean(rs, type);
	}
	
	protected T createBean(ResultSet rs, Class<T> type)
            throws SQLException {

        @SuppressWarnings("unchecked")
		T bean = (T) this.newInstance(type);
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        for (int col = 1; col <= cols; col++) {
        	String columnName = rsmd.getColumnLabel(col);
            if (null == columnName || 0 == columnName.length()) {
               columnName = rsmd.getColumnName(col);
            }
            if(columnToProperty.containsKey(columnName)){
            	PropertyDescriptor propertyDescriptor = columnToProperty.get(columnName);
            	Class<?> propertyType = propertyDescriptor.getPropertyType();
            	Object value = null;
            	if(propertyType != null) {
                    value = processColumn(rs,col,propertyType);
                    if (value == null && propertyType.isPrimitive()) {
                        value = primitiveDefaults.get(propertyType);
                    }
                }
            	callSetter(bean, propertyDescriptor.getWriteMethod(), value);
            }else if(columnToBeanProperty.containsKey(columnName)){
            	PropertyDescriptor propertyDescriptor = columnToBeanProperty.get(columnName);
            	Class<?> propertyType = propertyDescriptor.getPropertyType();
            	Object property = newInstance(propertyType);
            	PropertyDescriptor propertyDescriptor2 = columnToBean.get(columnName);
            	Class<?> propertyType2 = propertyDescriptor2.getPropertyType();
            	Object value = null;
            	if(propertyType2 != null) {
                    value = processColumn(rs,col,propertyType2);
                    if (value == null && propertyType2.isPrimitive()) {
                        value = primitiveDefaults.get(propertyType2);
                    }
                }
            	callSetter(property, propertyDescriptor2.getWriteMethod(), value);//设置关联对象中对应的属性
            	callSetter(bean, propertyDescriptor.getWriteMethod(), property);//设置需封装对象的属性
            }
        }
        return bean;
    }
	
	/**
	 * 反射创建一个对象
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	protected Object newInstance(Class<?> clazz) throws SQLException {
        try {
            return clazz.newInstance();

        } catch (Exception e) {
            throw new SQLException(
                "Cannot create " + clazz.getName() + ": " + e.getMessage());

        } 
    }
	
	/**
	 * 根据指定类型从游标返回对应类型的值
	 * @param rs
	 * @param index
	 * @param propType
	 * @return
	 * @throws SQLException
	 */
	protected Object processColumn(ResultSet rs, int index, Class<?> propType)
	        throws SQLException {

	        if ( !propType.isPrimitive() && rs.getObject(index) == null ) {
	            return null;
	        }

	        if (propType.equals(String.class)) {
	            return rs.getString(index);

	        } else if (
	            propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
	            return Integer.valueOf(rs.getInt(index));

	        } else if (
	            propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
	            return Boolean.valueOf(rs.getBoolean(index));

	        } else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
	            return Long.valueOf(rs.getLong(index));

	        } else if (
	            propType.equals(Double.TYPE) || propType.equals(Double.class)) {
	            return Double.valueOf(rs.getDouble(index));

	        } else if (
	            propType.equals(Float.TYPE) || propType.equals(Float.class)) {
	            return Float.valueOf(rs.getFloat(index));

	        } else if (
	            propType.equals(Short.TYPE) || propType.equals(Short.class)) {
	            return Short.valueOf(rs.getShort(index));

	        } else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
	            return Byte.valueOf(rs.getByte(index));

	        } else if (propType.equals(Timestamp.class)) {
	            return rs.getTimestamp(index);

	        } else if (propType.equals(SQLXML.class)) {
	            return rs.getSQLXML(index);

	        } else {
	            return rs.getObject(index);
	        }

	    }
	
	/**
	 * 根据对象set方法设置值
	 * @param target
	 * @param index
	 * @param columnName
	 * @param rs
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected void callSetter(Object target, Method setter, Object value)
            throws SQLException {

        if (setter == null) {
            return;
        }
        Class<?>[] params = setter.getParameterTypes();
        try {
            // convert types for some popular ones
            if (value instanceof java.util.Date) {
                final String targetType = params[0].getName();
                if ("java.sql.Date".equals(targetType)) {
                    value = new java.sql.Date(((java.util.Date) value).getTime());
                } else
                if ("java.sql.Time".equals(targetType)) {
                    value = new java.sql.Time(((java.util.Date) value).getTime());
                } else
                if ("java.sql.Timestamp".equals(targetType)) {
                    Timestamp tsValue = (Timestamp) value;
                    int nanos = tsValue.getNanos();
                    value = new java.sql.Timestamp(tsValue.getTime());
                    ((Timestamp) value).setNanos(nanos);
                }
            } else
            if (value instanceof String && params[0].isEnum()) {
                value = Enum.valueOf(params[0].asSubclass(Enum.class), (String) value);
            }

            // Don't call setter if the value object isn't the right type
            if (this.isCompatibleType(value, params[0])) {
                setter.invoke(target, new Object[]{value});
            } else {
              throw new SQLException(
                  "类:"+target.getClass().getName()+"调用方法："+setter.getName()
                  +"错误，所需参数类型："+params[0].getName()+"提供值类型："+value.getClass());
            }

        } catch (Exception e) {
            throw new SQLException(
                this.getClass().getName()+"设置对象属性方法异常！");
        }
    }
	
	/**
	 * 判断是否属于基本类型
	 * @param value
	 * @param type
	 * @return
	 */
	protected boolean isCompatibleType(Object value, Class<?> type) {
        if (value == null || type.isInstance(value)) {
            return true;

        } else if (type.equals(Integer.TYPE) && value instanceof Integer) {
            return true;

        } else if (type.equals(Long.TYPE) && value instanceof Long) {
            return true;

        } else if (type.equals(Double.TYPE) && value instanceof Double) {
            return true;

        } else if (type.equals(Float.TYPE) && value instanceof Float) {
            return true;

        } else if (type.equals(Short.TYPE) && value instanceof Short) {
            return true;

        } else if (type.equals(Byte.TYPE) && value instanceof Byte) {
            return true;

        } else if (type.equals(Character.TYPE) && value instanceof Character) {
            return true;

        } else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
            return true;

        }
        return false;

    }
}
