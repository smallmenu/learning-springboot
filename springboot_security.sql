/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : springboot_security

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 10/04/2020 19:34:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码Hash',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Email',
  `last_login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `last_logined` timestamp(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `locked` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '1锁定，0未锁定',
  `state` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '1启用，0禁用',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `createdby` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `updatedby` int(10) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`, `state`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'windspider', '管理员', '$2a$10$7xspkfCj3yOuHlE6bhLavuFr5xVmDhxxhwR7DGtHLWoKerjTHhuS6', 'windspider@suosi.com.cn', '127.0.0.1', '2020-04-10 05:33:37', 0, 1, '2018-12-12 10:36:25', 1, '2020-04-10 05:33:37', 1);
INSERT INTO `admin` VALUES (9, 'niuchaoqun', 'fasdfasdf', '$2a$10$jsr6xoYHPLmfxZNdVlKrneAIMG7KyFQCv2b8rk5sG7br0gNRz3WoW', 'asdf@asdf.asdf', '', NULL, 0, 1, '2020-04-10 05:36:54', 1, '2020-04-10 05:36:54', 0);
INSERT INTO `admin` VALUES (10, 'niuchaoqun1', 'fasdfasdf', '$2a$10$SrAgjwD0mP6Y4HEQqQU5pOia1.dtNrg9ClAnBGjhzGg5UWCwd8i0i', 'asdf@asdf.asdf', '', NULL, 0, 1, '2020-04-10 09:35:40', 1, '2020-04-10 09:35:40', 0);
INSERT INTO `admin` VALUES (11, 'niuchaoqun3', 'fasdfasdf', '$2a$10$OB8Ahd4gTT..pWX2j2VE3OSg1nVzp8dOjhWWz2bwDfHRT8QegXxFy', 'asdf@asdf.asdf', '', NULL, 0, 1, '2020-04-10 09:36:22', 1, '2020-04-10 09:36:22', 0);

-- ----------------------------
-- Table structure for admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_login_log`;
CREATE TABLE `admin_login_log`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `admin_id` int(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录IP',
  `logined` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '登录时间',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否成功',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `adminid` int(10) UNSIGNED NOT NULL,
  `roleid` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (1, 1, 1);
INSERT INTO `admin_role` VALUES (2, 9, 2);
INSERT INTO `admin_role` VALUES (3, 10, 2);
INSERT INTO `admin_role` VALUES (4, 11, 2);
INSERT INTO `admin_role` VALUES (5, 11, 3);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(10) UNSIGNED NOT NULL DEFAULT 0 AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '管理员数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 1);
INSERT INTO `role` VALUES (2, '运营', 3);
INSERT INTO `role` VALUES (3, '推广', 1);

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `note` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `group_key`(`group`, `key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setting
-- ----------------------------
INSERT INTO `setting` VALUES (1, 'global', 'title', '风行数聚平台', '系统名称');
INSERT INTO `setting` VALUES (2, 'global', 'pageSize', '20', '系统默认分页条数');
INSERT INTO `setting` VALUES (3, 'spider', 'testOne', '11中文', '');
INSERT INTO `setting` VALUES (4, 'spider', 'TestTwo', '22中文', '');
INSERT INTO `setting` VALUES (5, 'data', 'size', '33中文', '');

SET FOREIGN_KEY_CHECKS = 1;
