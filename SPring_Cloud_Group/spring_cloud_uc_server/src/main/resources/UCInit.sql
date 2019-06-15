/*
Navicat MySQL Data Transfer

Source Server         : 阿里云运动加服务器
Source Server Version : 50717
Source Host           : 47.106.232.21:3306
Source Database       : system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-05-24 14:23:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ampq_message
-- ----------------------------
DROP TABLE IF EXISTS `ampq_message`;
CREATE TABLE `ampq_message` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `ampq_class` varchar(255) DEFAULT NULL,
  `ampq_memo` varchar(255) DEFAULT NULL,
  `ampq_que_name` varchar(255) DEFAULT NULL,
  `ampq_statue` int(11) DEFAULT NULL,
  `ampq_time` datetime DEFAULT NULL,
  `ampq_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lzh_admin
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin`;
CREATE TABLE `lzh_admin` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `user_uuid` int(32) DEFAULT NULL COMMENT '业务识别ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '登录名',
  `phone_number` varchar(20) DEFAULT NULL COMMENT '登陆手机号',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `head_picture` varchar(500) DEFAULT NULL COMMENT '头像地址',
  `update_date` date DEFAULT NULL COMMENT '更新时间',
  `add_date` date DEFAULT NULL COMMENT '添加时间',
  `brithday` varchar(11) DEFAULT NULL COMMENT '出生日期',
  `age` int(2) DEFAULT NULL COMMENT '用户年龄',
  `sex` int(1) DEFAULT NULL COMMENT '性别',
  `verify_flg` int(1) DEFAULT NULL COMMENT '验证标记',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lzh_admin_bankcard
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_bankcard`;
CREATE TABLE `lzh_admin_bankcard` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `card_uuid` varchar(32) DEFAULT NULL COMMENT '银行卡业务识别ID',
  `user_uuid` varchar(255) DEFAULT NULL COMMENT '绑定用户UUId',
  `reg_bank_name` varchar(100) DEFAULT NULL COMMENT '开户行名称',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '银行名称',
  `bank_phone` varchar(255) DEFAULT NULL COMMENT '预留手机',
  `card_num` varchar(255) DEFAULT NULL COMMENT '银行账号',
  `card_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `reg_card_time` datetime DEFAULT NULL COMMENT '绑定系统时间',
  `unreg_card_time` datetime DEFAULT NULL COMMENT '绑定系统时间',
  `agent_bank_card_status` int(1) DEFAULT NULL COMMENT '银行卡状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lzh_admin_inmail
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_inmail`;
CREATE TABLE `lzh_admin_inmail` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '站内信数据ID',
  `in_message_uuid` varchar(32) DEFAULT NULL COMMENT '站内信识别业务主键',
  `in_message_title` varchar(100) DEFAULT NULL COMMENT '标题',
  `in_message_systemtime` timestamp NULL DEFAULT NULL COMMENT '消息时间',
  `in_message_readflg` int(1) DEFAULT NULL COMMENT '读取状态',
  `in_message_owner` varchar(32) DEFAULT NULL COMMENT '所属用户',
  `in_message_memo` varchar(500) DEFAULT NULL COMMENT '站内信内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lzh_admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_login_log`;
CREATE TABLE `lzh_admin_login_log` (
  `id` int(11) NOT NULL,
  `logincount` int(11) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  `memo1` varchar(255) DEFAULT NULL,
  `memo2` varchar(255) DEFAULT NULL,
  `memo3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lzh_admin_menus_rights
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_menus_rights`;
CREATE TABLE `lzh_admin_menus_rights` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `classes` int(11) NOT NULL,
  `parentid` int(11) NOT NULL,
  `permission` varchar(255) COLLATE utf8_bin NOT NULL,
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=143 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='lzh_admin_menus_rights';

-- ----------------------------
-- Table structure for lzh_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_permission`;
CREATE TABLE `lzh_admin_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `permission` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `permission_url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=280 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='lzh_admin_permission';

-- ----------------------------
-- Table structure for lzh_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_role`;
CREATE TABLE `lzh_admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `role` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `flg` int(1) DEFAULT '0' COMMENT '状态标志',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='lzh_admin_role';

-- ----------------------------
-- Table structure for lzh_admin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_role_permission`;
CREATE TABLE `lzh_admin_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=466 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='lzh_admin_role_permission';

-- ----------------------------
-- Table structure for lzh_admin_sms
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_sms`;
CREATE TABLE `lzh_admin_sms` (
  `id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `sms_uuid` varchar(32) DEFAULT NULL COMMENT '业务数据主键',
  `in_phone_number` varchar(15) DEFAULT NULL COMMENT '消息手机号',
  `sms_content` varchar(400) DEFAULT NULL COMMENT '短信内容',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `sms_type` int(1) DEFAULT NULL COMMENT '消息类型',
  `sms_status` int(2) DEFAULT NULL COMMENT '消息状态',
  `sms_status_str` varchar(200) DEFAULT NULL COMMENT '消息状态描述',
  `verify_flg` int(1) DEFAULT NULL COMMENT '短信验证状态区分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for lzh_admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `lzh_admin_user_role`;
CREATE TABLE `lzh_admin_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='lzh_admin_user_role';
