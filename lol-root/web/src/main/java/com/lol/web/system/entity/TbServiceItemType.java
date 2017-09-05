package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务项目类型表
 * @author yangli
 */
@Entity
@Table(name = "tb_service_item_type")
public class TbServiceItemType implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4449519451414465472L;
	
	public static final String ITEM_TYPE_ID = "itemTypeId";
	public static final String ITEM_TYPE_NAME = "itemTypeName";
	public static final String ITEM_TYPE_DETAIL = "itemTypeDetail";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	
	/**主键**/
	private Integer itemTypeId;
	/**服务项目类型名称**/
	private String itemTypeName;
	/**说明**/
	private String itemTypeDetail;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbServiceItemType() {
	}

	public TbServiceItemType(String itemTypeName, String itemTypeDetail,
			Timestamp createTime, Timestamp updateTime) {
		this.itemTypeName = itemTypeName;
		this.itemTypeDetail = itemTypeDetail;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "item_type_id", unique = true, nullable = false)
	public Integer getItemTypeId() {
		return this.itemTypeId;
	}

	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	@Column(name = "item_type_name", nullable = false, length = 50)
	public String getItemTypeName() {
		return this.itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	@Column(name = "item_type_detail", length = 200)
	public String getItemTypeDetail() {
		return this.itemTypeDetail;
	}

	public void setItemTypeDetail(String itemTypeDetail) {
		this.itemTypeDetail = itemTypeDetail;
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