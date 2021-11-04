/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  BritishWaldo
 * Created: 20-Oct-2021
 */

DROP SCHEMA IF EXISTS `UserDatabase`;
CREATE SCHEMA IF NOT EXISTS `UserDatabase` DEFAULT CHARACTER SET latin1;
USE `UserDatabase`;

-- -----------------------------------------------------
-- Table `UserDatabase`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UserDatabase`.`role` (
  `role_id` INT(11) NOT NULL,
  `role_name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`role_id`));

-- -----------------------------------------------------
-- Table `UserDatabase`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UserDatabase`.`user` (
  `email` VARCHAR(40) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT '1',
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `role` INT(11) NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role`)
    REFERENCES `UserDatabase`.`role` (`role_id`));


INSERT INTO `role` VALUES (1, 'System Administrator');
INSERT INTO `role` VALUES (2, 'Regular User');
INSERT INTO `role` VALUES (3, 'Company Administrator');


INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role`)
	VALUES ('cprg352+admin@gmail.com', true, 'Admin','Admin', 'password', 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role`)
	VALUES ('cprg352+admin2@gmail.com', true, 'Admin2','Admin2', 'password', 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role`)
	VALUES ('cprg352+anne@gmail.com', true, 'Anne','Annerson', 'password', 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role`)
	VALUES ('cprg352+barb@gmail.com', true, 'Barb','Barber', 'password', 2);
