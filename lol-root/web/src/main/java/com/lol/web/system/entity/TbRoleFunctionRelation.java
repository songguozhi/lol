package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色功能关系表
 * @author yangli
 */
@Entity
@Table(name = "tb_role_function_relation")
public class TbRoleFunctionRelation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1795054206970098166L;
	public static final String RELATION_ID = "relationId";
	public static final String ROLE_ID = "roleId";
	public static final String ROLE = "role";
	public static final String FUNCTION_ID = "functionId";
	public static final String FUNCTION = "function";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	// Fields

	/**主键**/
	private Integer relationId;
	/**角色ID**/
	private TbSystemRole role;
	/**功能ID**/
	private TbSystemFunction function;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbRoleFunctionRelation() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "relation_id", unique = true, nullable = false)
	public Integer getRelationId() {
		return this.relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	public TbSystemRole getRole() {
		return this.role;
	}

	public void setRole(TbSystemRole role) {
		this.role = role;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="function_id")
	public TbSystemFunction getFunction() {
		return this.function;
	}

	public void setFunction(TbSystemFunction function) {
		this.function = function;
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