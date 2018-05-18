--数据库初始化脚本

--创建数据库
CREATE DATABASE kerberos;

use kerberos;

CREATE TABLE user(
`user_number` int NOT NULL AUTO_INCREMENT  PRIMARY KEY COMMENT '用户主键,自增',
`name` varchar(120)  NOT NULL UNIQUE KEY COMMENT '用户名',
`password` varchar(120)  NOT NULL COMMENT '密码'
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT  CHARSET=utf8 COMMENT='用户认证表';

CREATE table des_key(
`user_number` int NOT NULL   PRIMARY KEY COMMENT '用户主键',
`des` varchar(120)  NOT NULL COMMENT 'des_key'
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT  CHARSET=utf8 COMMENT='用户des_key表';