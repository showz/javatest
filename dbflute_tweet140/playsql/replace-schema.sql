CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_active` enum('YES','NO') DEFAULT 'YES' COMMENT 'active flag',
  `mail_address` varchar(255) NOT NULL COMMENT 'mail address',
  `password` varchar(64) NOT NULL COMMENT 'password hash',
  `created_at` datetime NOT NULL COMMENT 'created datetime',
  PRIMARY KEY (`id`),
  KEY `mail_address_index` (`mail_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tweet` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_pub` enum('YES','NO') DEFAULT 'NO' COMMENT 'public flag',
  `uid` int(11) NOT NULL COMMENT 'user id',
  `tweet` text NOT NULL COMMENT 'tweet text',
  `comment_num` int(11) NOT NULL COMMENT 'comment number',
  `created_at` datetime NOT NULL COMMENT 'created datetime',
  `updated_at` datetime NOT NULL COMMENT 'updated datetime',
  PRIMARY KEY (`id`),
  KEY `uid_index` (`uid`),
  KEY `created_at_index` (`created_at`),
  CONSTRAINT `tweet_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_pub` enum('YES','NO') DEFAULT 'YES' COMMENT 'public flag',
  `tid` int(11) NOT NULL COMMENT 'tweet id',
  `uid` int(11) NOT NULL COMMENT 'user id',
  `comment` text NOT NULL COMMENT 'comment',
  `created_at` datetime NOT NULL COMMENT 'created datetime',
  `updated_at` datetime NOT NULL COMMENT 'updated datetime',
  PRIMARY KEY (`id`),
  KEY `uid_index` (`uid`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;