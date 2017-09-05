package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 店铺信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_shop")
public class TbShop implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -942139986467893777L;
	public static final String SHOP_ID = "shopId";
	public static final String SHOP_NAME = "shopName";
	public static final String SHOP_ADDRESS = "shopAddress";
	public static final String LINK_MAN = "linkMan";
	public static final String LINK_PHONE = "linkPhone";
	public static final String SHOP_TYPE = "shopType";
	public static final String SHOP_STATUS = "shopStatus";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";

	/**主键**/
	private Integer shopId;
	/**店铺名称**/
	private String shopName;
	/**店铺地址**/
	private String shopAddress;
	/**联系人**/
	private String linkMan;
	/**联系电话**/
	private String linkPhone;
	/**店铺类型(1:普通店铺,2:连锁店铺)**/
	private Integer shopType;
	/**店铺状态(1:正常,0:冻结,-1:删除,-2:未初始化)**/
	private Integer shopStatus;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbShop() {
	}

	public TbShop(String shopName, String shopAddress, String linkMan,
			String linkPhone, Integer shopType, Integer shopStatus,
			Timestamp createTime, Timestamp updateTime) {
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.linkMan = linkMan;
		this.linkPhone = linkPhone;
		this.shopType = shopType;
		this.shopStatus = shopStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "shop_id", unique = true, nullable = false)
	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	@Column(name = "shop_name", nullable = false, length = 100)
	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "shop_address", nullable = false, length = 200)
	public String getShopAddress() {
		return this.shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	@Column(name = "link_man", nullable = false, length = 50)
	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "link_phone", nullable = false, length = 50)
	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	@Column(name = "shop_type", nullable = false)
	public Integer getShopType() {
		return this.shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	@Column(name = "shop_status", nullable = false)
	public Integer getShopStatus() {
		return this.shopStatus;
	}

	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
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