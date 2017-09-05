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
 * 店铺会员信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_shop_vip")
public class TbShopVip implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8586801276556191475L;
	public static final String VIP_ID = "vipId";
	public static final String SHOP_ID = "shopId";
	public static final String SHOP = "shop";
	public static final String USER_ID = "userId";
	public static final String USER = "user";
	public static final String VIP_NAME = "vipName";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String PRICE_DISCOUNT = "priceDiscount";
	public static final String BALANCE = "balance";
	public static final String VIP_POINTS = "vipPoints";
	public static final String REMARK = "remark";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	
	/**主键**/
	private Integer vipId;
	/**店铺ID**/
	private TbShop shop;
	/**消费者用户ID**/
	private TbUser user;
	/**会员别称**/
	private String vipName;
	/**价格折扣**/
	private Integer priceDiscount;
	/**加入时间**/
	private Timestamp startTime;
	/**结束时间**/
	private Timestamp endTime;
	/**余额**/
	private Float balance;
	/**会员卡积分**/
	private Integer vipPoints;
	/**备注**/
	private String remark;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbShopVip() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "vip_id", unique = true, nullable = false)
	public Integer getVipId() {
		return this.vipId;
	}

	public void setVipId(Integer vipId) {
		this.vipId = vipId;
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

	@Column(name = "vip_name", length = 50)
	public String getVipName() {
		return this.vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	@Column(name = "price_discount", nullable = false)
	public Integer getPriceDiscount() {
		return this.priceDiscount;
	}

	public void setPriceDiscount(Integer priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	@Column(name = "start_time", nullable = false, length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "balance", nullable = false)
	public Float getBalance() {
		return this.balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	@Column(name = "vip_points", nullable = false)
	public Integer getVipPoints() {
		return this.vipPoints;
	}

	public void setVipPoints(Integer vipPoints) {
		this.vipPoints = vipPoints;
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