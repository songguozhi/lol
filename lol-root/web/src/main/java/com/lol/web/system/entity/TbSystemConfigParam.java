package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统配置参数表
 * @author yangli
 */
@Entity
@Table(name = "tb_system_config_param")
public class TbSystemConfigParam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1178547932663078125L;
	public static final String PARAM_ID = "paramId";
	public static final String PARAM_NAME = "paramName";
	public static final String PARAM_KEY = "paramKey";
	public static final String PARAM_VALUE = "paramValue";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";

	/**主键**/
	private Integer paramId;
	/**参数名**/
	private String paramName;
	/**参数标识**/
	private String paramKey;
	/**参数值**/
	private String paramValue;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbSystemConfigParam() {
	}

	/** full constructor */
	public TbSystemConfigParam(String paramName, String paramKey,
			String paramValue, Timestamp createTime, Timestamp updateTime) {
		this.paramName = paramName;
		this.paramKey = paramKey;
		this.paramValue = paramValue;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "param_id", unique = true, nullable = false)
	public Integer getParamId() {
		return this.paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	@Column(name = "param_name", nullable = false, length = 100)
	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "param_key", nullable = false, length = 100)
	public String getParamKey() {
		return this.paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	@Column(name = "param_value", length = 200)
	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}