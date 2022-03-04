create database if not exists campus_contagion;

use campus_contagion;


-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
	`pid` integer,
	`uname` varchar(50),
	`password` varchar(50),
	`power-ups_used` integer,
	`date_created` date,
	`last_login` date,
	`times_extracted` integer,
	`players_infected` integer,
	`games_played` integer,
	PRIMARY KEY (`pid`)
);


-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
DROP TABLE IF EXISTS `power-ups`;

CREATE TABLE `power-ups` (
	`pid` integer,
	`pname` varchar(50),
	`description` varchar(200),
	`duration` integer,
	PRIMARY KEY (`pid`)
);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
DROP TABLE IF EXISTS `servers`;

CREATE TABLE `servers` (
	`sid` integer,
	`times_crashed` integer,
	`times_used` integer,
	`users_disconnected` integer,
    `times_private` integer,
    `times_public` integer,
	PRIMARY KEY (`sid`)
);
