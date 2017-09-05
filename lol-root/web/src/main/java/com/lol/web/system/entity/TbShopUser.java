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
 * 店铺用户信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_shop_user")
public class TbShopUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2347406190723288900L;
	public static final String SHOP_USER_ID = "shopUserId";
	public static final String SHOP_ID = "shopId";
	public static final String SHOP = "shop";
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_PWD = "loginPwd";
	public static final String REAL_NAME = "realName";
	public static final String WECHAT_CODE = "wechatCode";
	public static final String PHONE = "phone";
	public static final String SHOP_USER_STATUS = "shopUserStatus";
	public static final String SHOP_USER_TYPE = "shopUserType";
	public static final String LAST_LOGIN_IP = "lastLoginIp";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	
	/**主键**/
	private Integer shopUserId;
	/**店铺ID**/
	private TbShop shop;
	/**登录名**/
	private String loginName;
	/**登录密码**/
	private String loginPwd;
	/**真实姓名**/
	private String realName;
	/**微信统一登录标识**/
	private String wechatCode;
	/**联系电话**/
	private String phone;
	/**用户状态(1:正常,0:冻结,-1:删除)**/
	private Integer shopUserStatus;
	/**用户类型(1:店员,2:店长,3:理发师/烫发师等技师)**/
	private Integer shopUserType;
	/**最后一次登录时间**/
	private Timestamp lastLoginTime;
	/**最后一次登录IP**/
	private String lastLoginIp;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbShopUser() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "shop_user_id", unique = true, nullable = false)
	public Integer getShopUserId() {
		return this.shopUserId;
	}

	public void setShopUserId(Integer shopUserId) {
		this.shopUserId = shopUserId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shop_id")
	public TbShop getShop() {
		return this.shop;
	}

	public void setShop(TbShop shop) {
		this.shop = shop;
	}

	@Column(name = "login_name", unique = true, length = 100)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "login_pwd", length = 100)
	public String getLoginPwd() {
		return this.loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	@Column(name = "real_name", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "wechat_code", length = 200)
	public String getWechatCode() {
		return this.wechatCode;
	}

	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}

	@Column(name = "phone", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "shop_user_status", nullable = false)
	public Integer getShopUserStatus() {
		return this.shopUserStatus;
	}

	public void setShopUserStatus(Integer shopUserStatus) {
		this.shopUserStatus = shopUserStatus;
	}

	@Column(name = "shop_user_type", nullable = false)
	public Integer getShopUserType() {
		return this.shopUserType;
	}

	public void setShopUserType(Integer shopUserType) {
		this.shopUserType = shopUserType;
	}

	@Column(name = "last_login_time", length = 19)
	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "last_login_ip", length = 100)
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
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