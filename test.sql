/*
SQLyog Ultimate v11.5 (64 bit)
MySQL - 8.0.13 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `system_config` */

DROP TABLE IF EXISTS `system_config`;

CREATE TABLE `system_config` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `PARAM_CODE` varchar(64) DEFAULT NULL COMMENT '参数代码',
  `PARAM_NAME` varchar(128) DEFAULT NULL COMMENT '参数名称',
  `PARAM_VALUE` varchar(128) DEFAULT NULL COMMENT '参数值',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '备注',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_config` */

insert  into `system_config`(`id`,`PARAM_CODE`,`PARAM_NAME`,`PARAM_VALUE`,`REMARK`,`CREATE_USER`,`CREATE_TIME`) values ('1','cmd_retry_time','指令获取锁重试次数','3','指令消费获取锁时的重试机制','Andy','2018-05-18 14:56:40');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `house_Address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`name`,`password`,`house_Address`,`phone`,`email`) values (1,'dfndfn','jhdfghjd','sfdsf','dfsgdfhfgh','fgjnfdhdf'),(2,'wdfsfd','sfsf','fsf','sfs','sdgf'),(3,'sdf','sdfsfs','fsf','sfsf','sfss'),(4,'sdfbd','fgdgd','gdg','dgdgdg','dgdf');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
