-- 系统配置参数表
drop table if exists tb_system_config_param;
create table tb_system_config_param(
	param_id int auto_increment primary key,
    param_name varchar(100) not null comment '参数名',
	param_key varchar(100) not null comment '参数标识',
    param_value varchar(200) null comment '参数值',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'    
);
-- 系统字典表
drop table if exists tb_system_dictionary;
create table tb_system_dictionary(
	word_id int auto_increment primary key,
    word_cn varchar(100) not null comment '中文词条名',
	word_english varchar(100) not null comment '英文词条名',
    word_detail varchar(200) null comment '词条描述',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'    
);
-- 系统功能(权限)信息表
drop table if exists tb_system_function;
create table tb_system_function(
	function_id int auto_increment primary key,
	function_name varchar(100) not null comment '功能名称',
    function_type int not null default 1 comment '功能类型(1:前端,2:后端,3:其它)',
    function_key varchar(200) null comment '功能标识(URL、菜单序号等)',
    function_status int not null default 1 comment '功能状态(1:正常,0:冻结,-1:删除)',
    remark varchar(100) null comment '备注/预留字段',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 系统角色信息表
drop table if exists tb_system_role;
create table tb_system_role(
	role_id int auto_increment primary key,
    role_name varchar(100) not null comment '角色名称',
    role_detail varchar(200) null comment '角色说明',
    role_type int not null default 1 comment '角色类型(1:消费者角色,2:商家角色,3:系统角色)',
    role_status int not null default 1 comment '角色状态(1:正常,0:冻结,-1:删除)',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 角色功能关系表
drop table if exists tb_role_function_relation;
create table tb_role_function_relation(
	relation_id int auto_increment primary key,
    role_id int not null comment '角色ID',
    function_id int not null comment '功能ID',
	create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 消费者用户信息表
drop table if exists tb_user;
create table tb_user(
	user_id int auto_increment primary key,
    login_name varchar(100) null unique comment '登录名',
	login_pwd varchar(100) null comment '登录密码',
	real_name varchar(50) null comment '真实姓名',
	nick_name varchar(50) null comment '昵称',
    wechat_code varchar(200) null comment '微信统一登录标识',
    phone varchar(50) null comment '联系电话',
    user_area varchar(100) null comment '所在地区',
    user_status int not null default 1 comment '用户状态(1:正常,0:冻结,-1:删除,-2:未初始化)',
    last_login_time datetime null comment '最后一次登录时间',
    last_login_ip varchar(100) null comment '最后一次登录IP',
    create_time datetime not null default now() comment '创建/注册时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 店铺信息表
drop table if exists tb_shop;
create table tb_shop(
	shop_id int auto_increment primary key,
	shop_name varchar(100) not null comment '店铺名称',
    shop_address varchar(200) not null comment '店铺地址',
    link_man varchar(50) not null comment '联系人',
    link_phone varchar(50) not null comment '联系电话',
    shop_type int not null default 1 comment '店铺类型(1:普通店铺,2:连锁店铺)',
    shop_status int not null default 1 comment '店铺状态(1:正常,0:冻结,-1:删除,-2:未初始化)',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
 );
 -- 店铺用户角色关系表
drop table if exists tb_shop_user_role_relation;
create table tb_shop_user_role_relation(
	relation_id int auto_increment primary key,
    shop_user_id int not null comment '店铺用户ID',
    role_id int not null comment '角色ID',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 店铺用户信息表
drop table if exists tb_shop_user;
create table tb_shop_user(
	shop_user_id int auto_increment primary key,
	shop_id int not null comment '店铺ID',
    login_name varchar(100) null unique comment '登录名',
	login_pwd varchar(100) null comment '登录密码',
	real_name varchar(50) null comment '真实姓名',
    wechat_code varchar(200) null comment '微信统一登录标识',
    phone varchar(50) null comment '联系电话',
    shop_user_status int not null default 1 comment '用户状态(1:正常,0:冻结,-1:删除)',
    shop_user_type int not null default 1 comment '用户类型(1:店员,2:店长,3:理发师/烫发师等技师)',
    last_login_time datetime null comment '最后一次登录时间',
    last_login_ip varchar(100) null comment '最后一次登录IP',
    create_time datetime not null default now() comment '创建/注册时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 店铺会员信息表
drop table if exists tb_shop_vip;
create table tb_shop_vip(
	vip_id int auto_increment primary key,
    shop_id int not null comment '店铺ID',
    user_id int not null comment '消费者用户ID',
    vip_name varchar(50) null comment '会员别称',
    price_discount int not null default 100 comment '价格折扣',
    start_time datetime not null default now() comment '加入时间',
    end_time datetime null default null comment '结束时间',
    balance float not null default 0 comment '余额',
    vip_points int not null default 0 comment '会员卡积分',
    remark varchar(100) null comment '备注',
	create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 店铺服务项目信息表
drop table if exists tb_shop_service_item;
create table tb_shop_service_item(
	item_id int auto_increment primary key,
    shop_id int not null comment '店铺ID',
    item_name varchar(100) not null comment '服务项目名称',
    item_type_id int not null comment '服务项目类型ID',
    item_price float not null default 0.0 comment '服务项目价格',
    item_discount int not null default 100 comment '服务项目折扣',
    item_detail varchar(200) null comment '服务项目详细',
    item_status int not null default 1 comment '服务状态(1:正常,0:停用)',
	create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 服务项目类型表
drop table if exists tb_service_item_type;
create table tb_service_item_type(
	item_type_id int auto_increment primary key,
    item_type_name varchar(50) not null comment '服务项目类型名称',
    item_type_detail varchar(200) null comment '说明',
	create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'
);
-- 服务消费单信息表
drop table if exists tb_service_order;
create table tb_service_order(
	service_order_id int auto_increment primary key,
    order_type int not null default 0 comment '消费类型(0:临时消费,1:会员消费)',
    shop_id int not null comment '店铺ID',
    user_id int null comment '消费者用户ID',
    shop_user_id int not null comment '店铺用户ID',
    item_id int not null comment '服务项目ID',
    original_price float not null comment '原始价格',
    discount int not null default 100 comment '折扣',
    fact_price float not null comment '实际价格',
 	create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'   
);
-- 用户充值单信息表
drop table if exists tb_recharge_order;
create table tb_recharge_order(
	recharge_order_id int auto_increment primary key,
    shop_id int not null comment '店铺ID',
    user_id int not null comment '消费者用户ID',
    vip_id int not null comment '店铺会员ID',
    shop_user_id int not null comment '店铺用户ID',
    original_money float not null default 0 comment '原始金额',
    gift_money float not null default 0 comment '赠送金额',
    fact_money float not null default 0 comment '实际充值金额',
    recharge_balance float not null comment '充值后余额',
    remark varchar(200) null comment '备注',
 	create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() on update now() comment '更新时间'       
);