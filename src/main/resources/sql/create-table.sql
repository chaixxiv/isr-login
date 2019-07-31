CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_time` datetime NOT NULL,
  `user` varchar(100) NOT NULL,
  `attribute1` varchar(45) DEFAULT NULL,
  `attribute2` varchar(45) DEFAULT NULL,
  `attribute3` varchar(45) DEFAULT NULL,
  `attribute4` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `login_time_idx` (`login_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100010 DEFAULT CHARSET=latin1