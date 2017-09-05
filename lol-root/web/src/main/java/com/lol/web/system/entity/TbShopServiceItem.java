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
 * 店铺服务项目信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_shop_service_item")
public class TbShopServiceItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 994601107115177948L;
	public static final String ITEM_ID = "itemId";
	public static final String SHOP_ID = "shopId";
	public static final String SHOP = "shop";
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_TYPE_ID = "itemTypeId";
	public static final String ITEM_TYPE = "itemType";
	public static final String ITEM_PRICE = "itemPrice";
	public static final String ITEM_DISCOUNT = "itemDiscount";
	public static final String ITEM_DETAIL = "itemDetail";
	public static final String ITEM_STATUS = "itemStatus";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";

	/**主键**/
	private Integer itemId;
	/**店铺ID**/
	private TbShop shop;
	/**服务项目名称**/
	private String itemName;
	/**服务项目类型**/
	private TbServiceItemType itemType;
	/**服务项目价格**/
	private Float itemPrice;
	/**服务项目折扣**/
	private Integer itemDiscount;
	/**服务项目详细**/
	private String itemDetail;
	/**服务状态(1:正常,0:停用)**/
	private Integer itemStatus;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbShopServiceItem() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "item_id", unique = true, nullable = false)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shop_id")
	public TbShop getShop() {
		return this.shop;
	}

	public void setShop(TbShop shop) {
		this.shop = shop;
	}

	@Column(name = "item_name", nullable = false, length = 100)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="item_type_id")
	public TbServiceItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(TbServiceItemType itemType) {
		this.itemType = itemType;
	}

	@Column(name = "item_price", nullable = false, precision = 12, scale = 0)
	public Float getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Column(name = "item_discount", nullable = false)
	public Integer getItemDiscount() {
		return this.itemDiscount;
	}

	public void setItemDiscount(Integer itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	@Column(name = "item_detail", length = 200)
	public String getItemDetail() {
		return this.itemDetail;
	}

	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}

	@Column(name = "item_status", nullable = false)
	public Integer getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
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