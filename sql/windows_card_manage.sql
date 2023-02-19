/*
 Navicat Premium Data Transfer

 Source Server         : local MySQL
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : windows_card_manage

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 18/02/2023 12:38:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'card id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT 'card create user',
  `card_type` int(11) NULL DEFAULT NULL COMMENT 'card type：1.card package，2.card',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'card name',
  `card_of_is` int(11) NULL DEFAULT NULL COMMENT 'card ascription switch：0.no ascription，1.ascription：desktop no show',
  `card_of_id` int(11) NULL DEFAULT NULL COMMENT 'when it is a card：ascription card package id',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'when it is a card：card content',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create time',
  `update_time` datetime NULL DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `account` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user account',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user password',
  `salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'salt：for password encryption',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录用户数据表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
