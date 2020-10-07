
-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: xdawo.com    Database: gloves
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_dept`
--

DROP TABLE IF EXISTS `t_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_id` varchar(45) DEFAULT NULL,
  `dept_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_dict_nation`
--

DROP TABLE IF EXISTS `t_dict_nation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dict_nation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(8) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc`
--

DROP TABLE IF EXISTS `t_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) DEFAULT NULL COMMENT '文档标识符',
  `name` varchar(128) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL COMMENT '文档类型',
  `type_name` varchar(128) DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL COMMENT '版本号',
  `create_time` timestamp NULL DEFAULT NULL,
  `structure` longtext,
  `html` longtext,
  `create_user_id` varchar(45) DEFAULT NULL,
  `create_user_name` varchar(45) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `layout` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=283 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_attr`
--

DROP TABLE IF EXISTS `t_doc_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_attr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) NOT NULL COMMENT '文档标识符',
  `version` tinyint(4) DEFAULT NULL COMMENT '版本号',
  `node_id` varchar(64) DEFAULT NULL COMMENT '组件标识符',
  `node_name` varchar(45) DEFAULT NULL,
  `color` varchar(10) DEFAULT NULL COMMENT 'JSON格式属性内容',
  `default_value` varchar(45) DEFAULT NULL,
  `font_size` varchar(5) DEFAULT NULL,
  `text_align` varchar(10) DEFAULT NULL,
  `text_type` varchar(10) DEFAULT NULL,
  `width` varchar(10) DEFAULT NULL,
  `regex` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `style` varchar(45) DEFAULT NULL,
  `score` decimal(4,2) DEFAULT NULL,
  `plan` varchar(45) DEFAULT NULL,
  `mode` varchar(45) DEFAULT NULL,
  `required` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15812 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_check`
--

DROP TABLE IF EXISTS `t_doc_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  `create_user_id` varchar(45) DEFAULT NULL,
  `create_user_name` varchar(45) DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6),
  `check_user_id` varchar(45) DEFAULT NULL,
  `check_user_name` varchar(45) DEFAULT NULL,
  `check_time` timestamp(6) NULL DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `t_dept_id` varchar(45) DEFAULT NULL,
  `t_dept_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_control`
--

DROP TABLE IF EXISTS `t_doc_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_control` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `control_id` varchar(45) DEFAULT NULL,
  `source_id` varchar(45) DEFAULT NULL,
  `source_name` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `font_size` varchar(45) DEFAULT NULL,
  `dept_id` varchar(45) DEFAULT NULL,
  `regex` varchar(500) DEFAULT NULL,
  `width` varchar(45) DEFAULT NULL,
  `text_type` varchar(45) DEFAULT NULL,
  `text_align` varchar(45) DEFAULT NULL,
  `default_value` varchar(128) DEFAULT NULL,
  `dept_name` varchar(512) DEFAULT NULL,
  `mode` varchar(45) DEFAULT NULL,
  `create_user_id` varchar(45) DEFAULT NULL,
  `create_user_name` varchar(45) DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_update_time` timestamp(6) NULL DEFAULT NULL,
  `last_update_user_id` varchar(45) DEFAULT NULL,
  `last_update_user_name` varchar(45) DEFAULT NULL,
  `type_name` varchar(45) DEFAULT NULL,
  `type_id` varchar(45) DEFAULT NULL,
  `type_command` varchar(45) DEFAULT NULL,
  `style` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_control_option`
--

DROP TABLE IF EXISTS `t_doc_control_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_control_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `control_id` varchar(45) DEFAULT NULL,
  `label` varchar(45) DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_control_type`
--

DROP TABLE IF EXISTS `t_doc_control_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_control_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `label` varchar(45) DEFAULT NULL,
  `command` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_data`
--

DROP TABLE IF EXISTS `t_doc_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) NOT NULL COMMENT '文档标识',
  `version` int(11) DEFAULT NULL COMMENT '文档版本',
  `node_id` varchar(64) DEFAULT NULL COMMENT '组件唯一标识符',
  `source_id` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL COMMENT '数据源或者控件名称',
  `value` varchar(512) DEFAULT NULL COMMENT '组件内容',
  `type` varchar(45) DEFAULT NULL,
  `regex` varchar(45) DEFAULT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `total_group_name` varchar(45) DEFAULT NULL,
  `plan` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16640 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_manage`
--

DROP TABLE IF EXISTS `t_doc_manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) NOT NULL,
  `doc_name` varchar(128) DEFAULT NULL,
  `last_version` tinyint(4) DEFAULT NULL,
  `dept_id` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `type_name` varchar(128) DEFAULT NULL,
  `dept_name` varchar(45) DEFAULT NULL,
  `create_user_id` varchar(45) DEFAULT NULL,
  `create_user_name` varchar(45) DEFAULT NULL,
  `last_update_user_id` varchar(45) DEFAULT NULL,
  `last_update_user_name` varchar(45) DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6),
  `last_update_time` timestamp(6) NULL DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_official`
--

DROP TABLE IF EXISTS `t_doc_official`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_official` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `type_name` varchar(128) DEFAULT NULL,
  `doc_name` varchar(128) DEFAULT NULL,
  `dept_id` varchar(45) DEFAULT NULL,
  `dept_name` varchar(45) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `check_user_id` varchar(45) DEFAULT NULL,
  `check_user_name` varchar(45) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_options`
--

DROP TABLE IF EXISTS `t_doc_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) DEFAULT NULL,
  `doc_version` tinyint(4) DEFAULT NULL,
  `node_id` varchar(45) DEFAULT NULL,
  `label` varchar(45) DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6669 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_source`
--

DROP TABLE IF EXISTS `t_doc_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_id` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `type` varchar(8) DEFAULT 'JSON' COMMENT '数据格式（JSON、SQL、URL），默认JSON',
  `extra` varchar(500) DEFAULT NULL COMMENT '附加数据',
  `content` text COMMENT '数据源内容，JSON、SQL脚本、URL地址',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `status` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_source_list`
--

DROP TABLE IF EXISTS `t_doc_source_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_source_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(45) DEFAULT NULL,
  `doc_version` tinyint(4) DEFAULT NULL,
  `source_id` varchar(45) DEFAULT NULL,
  `node_id` varchar(45) DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_doc_type`
--

DROP TABLE IF EXISTS `t_doc_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_doc_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` varchar(45) DEFAULT NULL,
  `label` varchar(45) DEFAULT NULL,
  `parent_type_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_patient_data`
--

DROP TABLE IF EXISTS `t_patient_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_patient_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_doc_id` varchar(45) DEFAULT NULL,
  `patient_doc_version` varchar(45) DEFAULT NULL,
  `node_id` varchar(45) DEFAULT NULL,
  `source_id` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `total_group_name` varchar(45) DEFAULT NULL,
  `regex` varchar(45) DEFAULT NULL,
  `plan` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37600 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_patient_doc`
--

DROP TABLE IF EXISTS `t_patient_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_patient_doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_doc_id` varchar(45) DEFAULT NULL,
  `last_patient_doc_version` tinyint(4) DEFAULT NULL,
  `doc_id` varchar(45) DEFAULT NULL,
  `doc_version` tinyint(4) DEFAULT NULL,
  `doc_type` varchar(45) DEFAULT NULL,
  `doc_name` varchar(128) DEFAULT NULL,
  `patient_id` varchar(45) DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6),
  `create_user_id` varchar(45) DEFAULT NULL,
  `create_user_name` varchar(45) DEFAULT NULL,
  `last_update_user_id` varchar(45) DEFAULT NULL,
  `last_upate_user_name` varchar(45) DEFAULT NULL,
  `last_update_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_patient_doc_list`
--

DROP TABLE IF EXISTS `t_patient_doc_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_patient_doc_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_doc_id` varchar(45) DEFAULT NULL,
  `patient_doc_version` tinyint(4) DEFAULT NULL,
  `create_user_id` varchar(45) DEFAULT NULL,
  `create_user_name` varchar(45) DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `html` longtext,
  `structure` longtext,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_patient_info`
--

DROP TABLE IF EXISTS `t_patient_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_patient_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` varchar(45) DEFAULT NULL,
  `real_name` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `id_no` varchar(45) DEFAULT NULL,
  `doctor_id` varchar(45) DEFAULT NULL,
  `doctor_name` varchar(45) DEFAULT NULL,
  `dept_id` varchar(45) DEFAULT NULL,
  `dept_name` varchar(45) DEFAULT NULL,
  `organization_id` varchar(45) DEFAULT NULL,
  `organization_name` varchar(45) DEFAULT NULL,
  `diagnosis` varchar(45) DEFAULT NULL,
  `bed_no` varchar(45) DEFAULT NULL,
  `ward_id` varchar(45) DEFAULT NULL,
  `ward_name` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `admitted_time` timestamp(6) NULL DEFAULT NULL,
  `days` varchar(45) DEFAULT NULL,
  `level` varchar(4) DEFAULT NULL COMMENT '护理等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23466 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_patient_info_nc`
--

DROP TABLE IF EXISTS `t_patient_info_nc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_patient_info_nc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` varchar(20) NOT NULL DEFAULT '' COMMENT '患者id',
  `card_number` varchar(20) NOT NULL DEFAULT '' COMMENT '就诊卡号',
  `admission_number` varchar(30) NOT NULL DEFAULT '' COMMENT '住院号',
  `dept_code` varchar(30) DEFAULT NULL COMMENT '部门科室code',
  `ward_code` varchar(30) DEFAULT '' COMMENT '病区code',
  `room_code` varchar(30) DEFAULT NULL COMMENT '房间code',
  `bed_code` varchar(30) DEFAULT '' COMMENT '床位code',
  `bed_number` varchar(20) DEFAULT NULL COMMENT '床位号',
  `real_name` varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
  `gender` int(1) NOT NULL DEFAULT '2' COMMENT '性别 0：女 1：男  2：保密',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_no` varchar(30) DEFAULT NULL COMMENT '身份证号码',
  `doctor_id` varchar(30) NOT NULL DEFAULT '' COMMENT '就诊医生id',
  `doctor_name` varchar(30) DEFAULT NULL COMMENT '就诊医生姓名',
  `nurse_id` varchar(30) DEFAULT NULL COMMENT '责任护士id',
  `nurse_name` varchar(30) DEFAULT NULL COMMENT '责任护士姓名',
  `visit_time` datetime NOT NULL DEFAULT '1979-01-01 00:00:00' COMMENT '就诊时间',
  `level_code` varchar(30) DEFAULT '' COMMENT '护理等级',
  `property_code` varchar(20) DEFAULT NULL COMMENT '患者属性code',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '在院状态： 1 在院 2出院  99 作废',
  `settlement_status` int(1) NOT NULL DEFAULT '0' COMMENT '结算状态：  0：否  1：是',
  `is_dead` int(1) NOT NULL DEFAULT '0' COMMENT '死亡标志： 0：否  1：是',
  `is_poor` int(1) DEFAULT '0' COMMENT '是否是贫困户（建档立卡）0：无  1：建档已脱贫 2：建档未脱贫',
  `dead_time` datetime DEFAULT NULL COMMENT '死亡时间',
  `admission_time` datetime DEFAULT '1979-01-01 00:00:00' COMMENT '入院时间',
  `pre_discharge_date` date DEFAULT NULL COMMENT '预出院日期',
  `discharge_disposition_code` varchar(30) DEFAULT NULL COMMENT '出院方式',
  `discharge_time` datetime DEFAULT NULL COMMENT '出院时间',
  `discharge_summary` varchar(200) DEFAULT NULL COMMENT '出院总结',
  `revisit_status` int(1) NOT NULL DEFAULT '-1' COMMENT '-1: 无   1：已回访  2：待回访',
  `dietary_situation` varchar(255) DEFAULT NULL COMMENT '饮食情况',
  `special_dietary` varchar(255) DEFAULT NULL COMMENT '特殊饮食',
  `balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '预交金费用余额',
  `total_costs` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '医疗总费用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `show_index` int(10) DEFAULT NULL COMMENT '床位排序',
  `revisit_remind_time` datetime DEFAULT NULL COMMENT '回访提醒时间',
  `additionalid` int(11) DEFAULT NULL COMMENT '南昌新加的字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `patient_id` (`patient_id`) USING BTREE,
  KEY `card_number` (`card_number`) USING BTREE,
  KEY `admission_number` (`admission_number`) USING BTREE,
  KEY `dept_code` (`dept_code`) USING BTREE,
  KEY `ward_code` (`ward_code`,`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23466 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='住院患者信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_patient_mark`
--

DROP TABLE IF EXISTS `t_patient_mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_patient_mark` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` varchar(45) DEFAULT NULL,
  `mark_name` varchar(45) DEFAULT NULL,
  `mark_value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `doctor_id` varchar(45) DEFAULT NULL,
  `doctor_name` varchar(45) DEFAULT NULL,
  `dept_id` varchar(45) DEFAULT NULL,
  `dept_name` varchar(45) DEFAULT NULL,
  `organization_id` varchar(45) DEFAULT NULL,
  `organization_name` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-07 12:07:56

