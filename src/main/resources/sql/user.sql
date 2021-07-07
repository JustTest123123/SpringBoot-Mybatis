/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : ddmysql

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 07/07/2021 19:46:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `passWord` varchar(50) NOT NULL,
  `realName` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'eaae7', 'ddd', 'realName');
INSERT INTO `user` VALUES (2, '1', '21', '22');
INSERT INTO `user` VALUES (11, '10', '2', '22');
INSERT INTO `user` VALUES (12, '1', '21', '22');
INSERT INTO `user` VALUES (13, '1', '21', '22');
INSERT INTO `user` VALUES (14, '1', '21', '22');
INSERT INTO `user` VALUES (15, '1', '21', '22');
INSERT INTO `user` VALUES (16, '1', '21', '22');
INSERT INTO `user` VALUES (17, '1', '21', '22');
INSERT INTO `user` VALUES (18, '1', '21', '22');
INSERT INTO `user` VALUES (19, '1', '21', '22');
INSERT INTO `user` VALUES (20, '1', '21', '22');
INSERT INTO `user` VALUES (21, '1', '21', '22');
INSERT INTO `user` VALUES (22, '1', '21', '22');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
