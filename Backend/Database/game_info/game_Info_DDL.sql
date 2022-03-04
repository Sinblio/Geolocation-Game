create database if not exists game_info;

use game_info;

SET FOREIGN_KEY_CHECKS = 0;

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
	`uid` integer NOT NULL AUTO_INCREMENT,
	`uname` varchar(50) UNIQUE,
	`password` varchar(50),
    `active` BIT NOT NULL,
	PRIMARY KEY (`uid`)
);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
DROP TABLE IF EXISTS `user_stats`;

CREATE TABLE `user_stats` (
	`uid` integer,
	`powerups_used` integer,
	`date_created` char(10),
	`last_login` char(10),
	`times_extracted` integer,
	`players_infected` integer,
	`games_played` integer,
	PRIMARY KEY (`uid`),
    FOREIGN KEY (`uid`) references `users`(`uid`)
);

CREATE TRIGGER dateCreated BEFORE INSERT ON `user_stats` FOR EACH ROW
  SET NEW.`date_created` = IFNULL(NEW.`date_created`, CURRENT_DATE);
  
CREATE TRIGGER lastLogin BEFORE INSERT ON `user_stats` FOR EACH ROW
  SET NEW.`last_login` = IFNULL(NEW.`last_login`, CURRENT_DATE);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
DROP TABLE IF EXISTS `power_ups`;

CREATE TABLE `power_ups` (
	`pid` integer,
	`pname` varchar(50),
    `team` char(4),
    `description` varchar(200),
	`duration` integer,
	PRIMARY KEY (`pid`)
);


