-- #success_user.sql文件内容，建立表，我的数据库在crud中
CREATE TABLE `success_user` (
  `uid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) DEFAULT NULL,
  `password` VARCHAR(64) DEFAULT NULL,
  `email` VARCHAR(200) DEFAULT NULL,
  `address` VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO success_user VALUES(3,'2','2','3','343')
