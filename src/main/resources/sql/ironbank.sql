-- MySQL Script generated by MySQL Workbench
-- Sat Sep  1 18:56:34 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `pass` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`admin` (
  `admin_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `pass` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`admin_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`account_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`account_type` (
  `id` BIGINT(11) NOT NULL,
  `name` ENUM('credit', 'deposit') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`account` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `balance` DECIMAL(8,2) NOT NULL DEFAULT 0,
  `rate` DOUBLE NOT NULL DEFAULT 0.0,
  `validity_from` DATE NOT NULL,
  `validity_to` DATE NOT NULL,
  `user_id` BIGINT(11) NOT NULL,
  `type_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `type_id`),
  INDEX `fk_account_user1_idx` (`user_id` ASC) ,
  INDEX `fk_account_account_type1_idx` (`type_id` ASC) ,
  CONSTRAINT `fk_account_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_account_account_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `mydb`.`account_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`new_account_request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`new_account_request` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `is_accepted` TINYINT NOT NULL DEFAULT 0,
  `is_declined` TINYINT NOT NULL DEFAULT 0,
  `rate` DOUBLE NOT NULL,
  `credit_limit` DECIMAL(8,2) NOT NULL DEFAULT 0,
  `user_id` BIGINT(11) NOT NULL,
  `type_id` BIGINT(11) NOT NULL,
  `admin_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `type_id`),
  INDEX `fk_new_account_request_user1_idx` (`user_id` ASC) ,
  INDEX `fk_new_account_request_account_type1_idx` (`type_id` ASC) ,
  INDEX `fk_new_account_request_admin1_idx` (`admin_id` ASC) ,
  CONSTRAINT `fk_new_account_request_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_new_account_request_account_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `mydb`.`account_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_new_account_request_admin1`
    FOREIGN KEY (`admin_id`)
    REFERENCES `mydb`.`admin` (`admin_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`credit_features`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`credit_features` (
  `credit_limit` DECIMAL(8,2) NOT NULL,
  `indebtedness` DECIMAL(8,2) NOT NULL,
  `account_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`account_id`),
  CONSTRAINT `fk_credit_features_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `mydb`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`transaction` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `amount` DECIMAL(8,2) NOT NULL,
  `sender_account_id` BIGINT(11) NOT NULL,
  `recipient_account_id` BIGINT(11) NOT NULL,
  `account_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transaction_account1_idx` (`account_id` ASC) ,
  CONSTRAINT `fk_transaction_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `mydb`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
