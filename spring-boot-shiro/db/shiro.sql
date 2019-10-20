/*
Navicat MySQL Data Transfer

Source Server         : ly
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2019-10-20 13:51:37
*/
CREATE DATABASE IF NOT EXISTS `shiro`;

USE `shiro`;



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `parent_id` bigint(20) default NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) default NULL COMMENT '菜单名称',
  `url` varchar(200) default NULL COMMENT '权限url，并不是真正的url,只是一个权限标识字符串',
  `type` int(11) default NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `order_num` int(11) default NULL COMMENT '排序',
  `has_delete` tinyint(4) default '0' COMMENT '是否被删除了 0：可用  1：被删除',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '0', '权限管理', 'permission', '0', null, '0', '2019-07-20 19:33:49');
INSERT INTO `permission` VALUES ('2', '1', '添加', 'permission:create', '1', null, '0', '2019-07-21 19:34:48');
INSERT INTO `permission` VALUES ('3', '1', '编辑', 'permission:update', '1', null, '0', '2019-07-21 19:35:15');
INSERT INTO `permission` VALUES ('4', '1', '查询', 'permission:select', '1', null, '0', '2019-07-21 19:36:11');
INSERT INTO `permission` VALUES ('5', '0', '用户管理', 'user', '0', null, '0', '2019-07-20 20:07:00');
INSERT INTO `permission` VALUES ('6', '0', '角色管理', 'role', '0', null, '0', null);
INSERT INTO `permission` VALUES ('7', '6', '查询', 'role:select', '1', null, '0', null);
INSERT INTO `permission` VALUES ('14', '5', '添加', 'user:add', '1', null, null, null);
INSERT INTO `permission` VALUES ('15', '5', '查看', 'user:select', '1', null, null, null);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `role_name` varchar(100) default NULL COMMENT '角色名称',
  `remark` varchar(100) default NULL COMMENT '备注',
  `has_delete` tinyint(4) default '0' COMMENT '是否被删除了 0：可用  1：被删除',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'Z05P4H', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('2', '3ZL1H8', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('3', 'PU56FK', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('4', 'MGDF4A', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('5', '3KJ1LV', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('6', 'I0IKAO', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('7', 'KQ25GX', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('8', '8FERKL', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('9', 'BPMYYV', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('10', 'IX5ZE2', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('11', '3CE0T0', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('12', 'OCNAFC', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('13', 'E0U7FK', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('14', 'JY62TV', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('15', 'X1G21Y', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('16', 'OKTDCP', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('17', 'EXHKGI', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('18', '7K4ZKX', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('19', 'VK69S4', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('20', 'A3JIVT', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('21', 'I1QIB3', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('22', 'KHTJAT', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('23', '9UHURA', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('24', '5VWY3J', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('25', 'FJFIB1', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('26', '976AYX', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('27', 'PQLQWD', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('28', '2A9HJB', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('29', 'ZVIX3N', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('30', '2ABNEY', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('31', 'M641SW', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('32', '3SO014', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('33', 'KGLL8B', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('34', 'YS1U3Y', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('35', 'JR8UJ5', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('36', '5C7ZCT', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('37', '0KTFN1', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('38', '5P013D', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('39', 'N2CLWP', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('40', 'U41V8I', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('41', 'X2LS6H', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('42', 'W2LR36', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('43', 'KAT6EH', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('44', '8OQN1A', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('45', '9JTI2U', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('46', '1O71II', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('47', '2QH6IY', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('48', 'BOF23B', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('49', '87C4KG', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('50', 'KJZAHJ', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('51', '9OKUGU', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('52', 'RCE1YO', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('53', 'JMHMNB', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('54', 'OHC9AN', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('55', 'E36OWF', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('56', 'ERNWJZ', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('57', 'DU59S7', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('58', 'MGHZKT', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('59', 'CDSVZ8', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('60', 'BT45GS', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('61', 'JEBF6M', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('62', 'M78HQ7', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('63', 'SDJLBR', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('64', 'W9JWVO', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('65', 'SXAM9F', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('66', 'FTSI8N', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('67', 'KVMHJ9', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('68', 'QTX76C', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('69', '4OXOJQ', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('70', '24CF4C', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('71', 'CPF34D', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('72', 'IFMS49', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('73', 'VMHKC3', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('74', 'GYD25M', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('75', 'NDYL4S', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('76', 'MQRNXR', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('77', '0PJMHI', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('78', '30OG6I', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('79', '1N70F5', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('80', 'FN2BF7', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('81', 'Q0WFGZ', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('82', 'JR5HZF', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('83', '7Q3B76', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('84', '7IW0EY', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('85', 'L2MUDA', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('86', 'CWGLNE', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('87', '391G47', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('88', 'NNAKWT', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('89', 'DF20T2', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('90', 'V8ITJ9', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('91', 'RYJUKA', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('92', 'S46LD2', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('93', '8XYZ4L', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('94', 'MDXJZ8', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('95', '7B05P1', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('96', '28X071', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('97', 'MZ3I7L', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('98', '9K3QDP', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('99', 'CM5USD', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('100', 'IEG5EK', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('101', 'MFA60H', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('102', 'CCPG7O', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('103', 'RQEU0G', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('104', 'B5WZ86', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('105', '9RZNBO', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('106', 'F4CBM2', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('107', 'JIX3O3', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('108', 'H6C83U', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('109', 'USCCQN', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('110', '2FV4YG', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('111', 'FSMS1T', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('112', 'X9HNS1', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('113', 'RRIAWM', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('114', 'GDKOPG', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('115', '7OSUVS', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('116', 'DJJ41R', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('117', 'O4NT45', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('118', 'CDQNYX', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('119', 'QWAPO9', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('120', 'CXJZ9D', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('121', '27WUJ5', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('122', '30T1RN', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('123', 'ZXPQMV', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('124', 'GO3EPC', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('125', 'KSA3KM', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('126', 'CT46HX', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('127', '78IVUK', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('128', 'D4I6DC', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('129', 'N4QBBO', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('130', 'F5HZF5', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('131', 'GW52V6', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('132', '8NL1CO', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('133', 'EV56GR', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('134', 'DMZ2EU', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('135', 'X77DBG', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('136', 'A2IB4N', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('137', 'U9R237', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('138', 'R8WQ21', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('139', '114I8J', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('140', '2O851O', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('141', 'CQKPRQ', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('142', 'DOACYN', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('143', 'GCBLY0', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('144', '5TKE93', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('145', 'QESRH2', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('146', 'XGGV3S', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('147', 'MT8OQK', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('148', 'OOEZQO', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('149', '7Y8AOK', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('150', 'TEJIW0', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('151', 'COEWD5', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('152', 'LJY3MV', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('153', 'J0G7NM', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('154', '9CZVDA', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('155', 'CT2XGG', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('156', 'V1JLBU', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('157', 'AWLAOH', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('158', 'FN03G1', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('159', 'STOZXM', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('160', 'E28VOS', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('161', 'W52U1O', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('162', '9BRV4Z', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('163', 'LZ4P6Q', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('164', '4EMYWN', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('165', 'INOG8S', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('166', 'B5SI7H', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('167', 'VTJ7GM', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('168', 'S0PKON', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('169', '72PCN8', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('170', '7B0180', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('171', 'DSTSGX', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('172', 'BRXANG', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('173', 'B6ZF5H', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('174', 'W4X9IU', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('175', 'LHKGKI', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('176', 'VUKAS0', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('177', 'QLVHSK', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('178', 'FH6C70', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('179', 'G9Z2G0', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('180', 'RQETW0', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('181', 'F0U7HU', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('182', 'R8UKC1', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('183', '6Q3BAJ', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('184', 'THX63Z', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('185', 'LZ72RJ', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('186', 'GNWIVW', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('187', 'SA4QCF', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('188', '7PXK0D', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('189', 'T0JPXH', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('190', 'OWIWVP', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('191', 'WEAAJS', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('192', 'DHB6WY', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('193', '5VUNRT', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('194', 'TNRW8I', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('195', 'SHYE38', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('196', 'XY05O0', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('197', '000015', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('198', 'LK3RI8', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('199', 'OQJL9M', null, '0', '2019-06-16 19:30:00');
INSERT INTO `role` VALUES ('200', 'AJTI3W', null, '0', '2019-06-16 19:30:00');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `role_id` bigint(20) default NULL COMMENT '角色ID',
  `pid` bigint(20) default NULL COMMENT '权限ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('21', '3', '1');
INSERT INTO `role_permission` VALUES ('22', '3', '2');
INSERT INTO `role_permission` VALUES ('23', '3', '3');
INSERT INTO `role_permission` VALUES ('24', '3', '4');
INSERT INTO `role_permission` VALUES ('25', '2', '1');
INSERT INTO `role_permission` VALUES ('26', '2', '2');
INSERT INTO `role_permission` VALUES ('27', '2', '3');
INSERT INTO `role_permission` VALUES ('28', '2', '4');
INSERT INTO `role_permission` VALUES ('29', '2', '5');
INSERT INTO `role_permission` VALUES ('30', '2', '14');
INSERT INTO `role_permission` VALUES ('31', '2', '6');
INSERT INTO `role_permission` VALUES ('32', '2', '7');
INSERT INTO `role_permission` VALUES ('33', '1', '1');
INSERT INTO `role_permission` VALUES ('34', '1', '2');
INSERT INTO `role_permission` VALUES ('35', '1', '3');
INSERT INTO `role_permission` VALUES ('36', '1', '5');
INSERT INTO `role_permission` VALUES ('37', '1', '14');
INSERT INTO `role_permission` VALUES ('38', '1', '15');
INSERT INTO `role_permission` VALUES ('39', '1', '6');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) default NULL COMMENT '密码',
  `salt` varchar(20) default NULL COMMENT '盐',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `mobile` varchar(100) default NULL COMMENT '手机号',
  `status` tinyint(4) default NULL COMMENT '状态  0：禁用  1：正常',
  `has_delete` tinyint(4) default '0' COMMENT '是否被删除了 0：可用  1：被删除',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ly', 'a', null, null, null, null, '0', null);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `user_id` bigint(20) default NULL COMMENT '用户ID',
  `role_id` bigint(20) default NULL COMMENT '角色ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');


