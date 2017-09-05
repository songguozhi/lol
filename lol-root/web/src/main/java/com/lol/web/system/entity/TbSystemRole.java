package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统角色信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_system_role")
public class TbSystemRole implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4383799931599138617L;
	public static final String ROLE_ID = "roleId";
	public static final String ROLE_NAME = "roleName";
	public static final String ROLE_DETAIL = "roleDetail";
	public static final String ROLE_TYPE = "roleType";
	public static final String ROLE_STATUS = "roleStatus";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";

	/**主键**/
	private Integer roleId;
	/**角色名称**/
	private String roleName;
	/**角色说明**/
	private String roleDetail;
	/**角色类型(1:消费者角色,2:商家角色,3:系统角色)**/
	private Integer roleType;
	/**角色状态(1:正常,0:冻结,-1:删除)**/
	private Integer roleStatus;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbSystemRole() {
	}

	public TbSystemRole(String roleName, String roleDetail, Integer roleType,
			Integer roleStatus, Timestamp createTime, Timestamp updateTime) {
		this.roleName = roleName;
		this.roleDetail = roleDetail;
		this.roleType = roleType;
		this.roleStatus = roleStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name", nullable = false, length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_detail", length = 200)
	public String getRoleDetail() {
		return this.roleDetail;
	}

	public void setRoleDetail(String roleDetail) {
		this.roleDetail = roleDetail;
	}

	@Column(name = "role_type", nullable = false)
	public Integer getRoleType() {
		return this.roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	@Column(name = "role_status", nullable = false)
	public Integer getRoleStatus() {
		return this.roleStatus;
	}

	public void setRoleStatus(Integer roleStatus) {
		this.roleStatus = roleStatus;
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