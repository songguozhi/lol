package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统功能(权限)信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_system_function")
public class TbSystemFunction implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -325124287107749796L;
	public static final String FUNCTION_ID = "functionId";
	public static final String FUNCTION_NAME = "functionName";
	public static final String FUNCTION_TYPE = "functionType";
	public static final String FUNCTION_KEY = "functionKey";
	public static final String FUNCTION_STATUS = "functionStatus";
	public static final String REMARK = "remark";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";

	/**主键**/
	private Integer functionId;
	/**功能名称**/
	private String functionName;
	/**功能类型(1:前端,2:后端,3:其它)**/
	private Integer functionType;
	/**功能标识(URL、菜单序号等)**/
	private String functionKey;
	/**功能状态(1:正常,0:冻结,-1:删除)**/
	private Integer functionStatus;
	/**备注/预留字段**/
	private String remark;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbSystemFunction() {
	}

	public TbSystemFunction(String functionName, Integer functionType,
			String functionKey, Integer functionStatus, String remark,
			Timestamp createTime, Timestamp updateTime) {
		this.functionName = functionName;
		this.functionType = functionType;
		this.functionKey = functionKey;
		this.functionStatus = functionStatus;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "function_id", unique = true, nullable = false)
	public Integer getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	@Column(name = "function_name", nullable = false, length = 100)
	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "function_type", nullable = false)
	public Integer getFunctionType() {
		return this.functionType;
	}

	public void setFunctionType(Integer functionType) {
		this.functionType = functionType;
	}

	@Column(name = "function_key", length = 200)
	public String getFunctionKey() {
		return this.functionKey;
	}

	public void setFunctionKey(String functionKey) {
		this.functionKey = functionKey;
	}

	@Column(name = "function_status", nullable = false)
	public Integer getFunctionStatus() {
		return this.functionStatus;
	}

	public void setFunctionStatus(Integer functionStatus) {
		this.functionStatus = functionStatus;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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