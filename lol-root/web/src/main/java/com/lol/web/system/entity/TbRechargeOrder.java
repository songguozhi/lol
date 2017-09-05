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
 * 用户充值单信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_recharge_order")
public class TbRechargeOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 229669581545811411L;
	public static final String RECHARGE_ORDER_ID = "rechargeOrderId";
	public static final String SHOP_ID = "shopId";
	public static final String SHOP = "shop";
	public static final String USER_ID = "userId";
	public static final String USER = "user";
	public static final String VIP_ID = "vipId";
	public static final String VIP = "vip";
	public static final String SHOP_USER_ID = "shopUserId";
	public static final String SHOP_USER = "shopUser";
	public static final String ORIGINAL_MONEY = "originalMoney";
	public static final String GIFT_MONEY = "giftMoney";
	public static final String FACT_MONEY = "factMoney";
	public static final String RECHARGE_BALANCE = "rechargeBalance";
	public static final String REMARK = "remark";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	
	// Fields
	/**主键**/
	private Integer rechargeOrderId;
	/**店铺ID**/
	private TbShop shop;
	/**消费者用户ID**/
	private TbUser user;
	/**店铺会员ID**/
	private TbShopVip vip;
	/**店铺用户ID**/
	private TbShopUser shopUser;
	/**原始金额**/
	private Float originalMoney;
	/**赠送金额**/
	private Float giftMoney;
	/**实际充值金额**/
	private Float factMoney;
	/**充值后余额'**/
	private Float rechargeBalance;
	/**备注**/
	private String remark;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public TbRechargeOrder() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "recharge_order_id", unique = true, nullable = false)
	public Integer getRechargeOrderId() {
		return this.rechargeOrderId;
	}

	public void setRechargeOrderId(Integer rechargeOrderId) {
		this.rechargeOrderId = rechargeOrderId;
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
	@JoinColumn(name="vip_id")
	public TbShopVip getVip() {
		return this.vip;
	}

	public void setVip(TbShopVip vip) {
		this.vip = vip;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shop_user_id")
	public TbShopUser getShopUser() {
		return this.shopUser;
	}

	public void setShopUser(TbShopUser shopUser) {
		this.shopUser = shopUser;
	}

	@Column(name = "original_money", nullable = false)
	public Float getOriginalMoney() {
		return this.originalMoney;
	}

	public void setOriginalMoney(Float originalMoney) {
		this.originalMoney = originalMoney;
	}

	@Column(name = "gift_money", nullable = false)
	public Float getGiftMoney() {
		return this.giftMoney;
	}

	public void setGiftMoney(Float giftMoney) {
		this.giftMoney = giftMoney;
	}

	@Column(name = "fact_money", nullable = false)
	public Float getFactMoney() {
		return this.factMoney;
	}

	public void setFactMoney(Float factMoney) {
		this.factMoney = factMoney;
	}

	@Column(name = "recharge_balance", nullable = false, precision = 12, scale = 0)
	public Float getRechargeBalance() {
		return this.rechargeBalance;
	}

	public void setRechargeBalance(Float rechargeBalance) {
		this.rechargeBalance = rechargeBalance;
	}

	@Column(name = "remark", length = 200)
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