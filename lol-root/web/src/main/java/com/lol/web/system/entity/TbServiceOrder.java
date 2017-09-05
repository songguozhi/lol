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
 * 服务消费单信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_service_order")
public class TbServiceOrder implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3688270237085550524L;
	public static final String SERVICE_ORDER_ID = "serviceOrderId";
	public static final String ORDER_TYPE = "orderType";
	public static final String SHOP_ID = "shopId";
	public static final String SHOP = "shop";
	public static final String USER_ID = "userId";
	public static final String USER = "user";
	public static final String SHOP_USER_ID = "shopUserId";
	public static final String SHOP_USER = "shopUser";
	public static final String ITEM_ID = "itemId";
	public static final String ITEM = "item";
	public static final String ORIGINAL_PRICE = "originalPrice";
	public static final String DISCOUNT = "discount";
	public static final String FACT_PRICE = "factPrice";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	
	/**主键**/
	private Integer serviceOrderId;
	/**消费类型(0:临时消费,1:会员消费)**/
	private Integer orderType;
	/**店铺ID**/
	private TbShop shop;
	/**消费者用户ID**/
	private TbUser user;
	/**店铺用户ID**/
	private TbShopUser shopUser;
	/**服务项目ID**/
	private TbShopServiceItem item;
	/**原始价格**/
	private Float originalPrice;
	/**折扣**/
	private Integer discount;
	/**实际价格**/
	private Float factPrice;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbServiceOrder() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "service_order_id", unique = true, nullable = false)
	public Integer getServiceOrderId() {
		return this.serviceOrderId;
	}

	public void setServiceOrderId(Integer serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	@Column(name = "order_type", nullable = false)
	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shop_id")
	public TbShop getShop() {
		return this.shop;
	}

	public void setShop(TbShop shop) {
		this.shop = shop;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public TbUser getUser() {
		return this.user;
	}

	public void setUser(TbUser user) {
		this.user = user;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shop_user_id")
	public TbShopUser getShopUser() {
		return this.shopUser;
	}

	public void setShopUser(TbShopUser shopUser) {
		this.shopUser = shopUser;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	public TbShopServiceItem getItem() {
		return this.item;
	}

	public void setItem(TbShopServiceItem item) {
		this.item = item;
	}

	@Column(name = "original_price", nullable = false, precision = 12, scale = 0)
	public Float getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(Float originalPrice) {
		this.originalPrice = originalPrice;
	}

	@Column(name = "discount", nullable = false)
	public Integer getDiscount() {
		return this.discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	@Column(name = "fact_price", nullable = false, precision = 12, scale = 0)
	public Float getFactPrice() {
		return this.factPrice;
	}

	public void setFactPrice(Float factPrice) {
		this.factPrice = factPrice;
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