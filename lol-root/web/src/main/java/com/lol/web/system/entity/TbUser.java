package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消费者用户信息表
 * @author yangli
 */
@Entity
@Table(name = "tb_user")
public class TbUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4304006814019018618L;
	public static final String USER_ID = "userId";
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_PWD = "loginPwd";
	public static final String REAL_NAME = "realName";
	public static final String NICK_NAME = "nickName";
	public static final String WECHAT_CODE = "wechatCode";
	public static final String PHONE = "phone";
	public static final String USER_AREA = "userArea";
	public static final String USER_STATUS = "userStatus";
	public static final String LAST_LOGIN_TIME = "lastLoginTime";
	public static final String LAST_LOGIN_IP = "lastLoginIp";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";

	/**主键**/
	private Integer userId;
	/**登录名**/
	private String loginName;
	/**登录密码**/
	private String loginPwd;
	/**真实姓名**/
	private String realName;
	/**昵称**/
	private String nickName;
	/**微信统一登录标识**/
	private String wechatCode;
	/**联系电话**/
	private String phone;
	/**所在地区**/
	private String userArea;
	/**用户状态(1:正常,0:冻结,-1:删除,-2:未初始化)**/
	private Integer userStatus;
	/**最后一次登录时间**/
	private Timestamp lastLoginTime;
	/**最后一次登录IP**/
	private String lastLoginIp;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	public TbUser() {
	}

	public TbUser(String loginName, String loginPwd, String realName,
			String nickName, String wechatCode, String phone, String userArea,
			Integer userStatus, Timestamp lastLoginTime, String lastLoginIp,
			Timestamp createTime, Timestamp updateTime) {
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.realName = realName;
		this.nickName = nickName;
		this.wechatCode = wechatCode;
		this.phone = phone;
		this.userArea = userArea;
		this.userStatus = userStatus;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@Column(name = "nick_name", length = 50)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	@Column(name = "user_area", length = 100)
	public String getUserArea() {
		return this.userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	@Column(name = "user_status", nullable = false)
	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
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