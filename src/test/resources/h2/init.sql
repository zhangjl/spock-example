CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '城市',
  `state` varchar(64) NOT NULL DEFAULT '' COMMENT '省',
  `country` varchar(128) NOT NULL DEFAULT '' COMMENT '国家',
  PRIMARY KEY (`id`)
) COMMENT='城市信息';