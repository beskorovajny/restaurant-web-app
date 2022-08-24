-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema restaurant
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `restaurant` ;

-- -----------------------------------------------------
-- Schema restaurant
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `restaurant` DEFAULT CHARACTER SET utf8 ;
USE `restaurant` ;

-- -----------------------------------------------------
-- Table `restaurant`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`role` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`user` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NULL,
  `password` VARCHAR(65) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) INVISIBLE,
  INDEX `idx_first_name_last_name` (`first_name` ASC, `last_name` ASC) INVISIBLE,
  UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC) VISIBLE,
  INDEX `fk_person_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_person_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `restaurant`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`credit_card`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`credit_card` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`credit_card` (
  `card_number` INT NOT NULL,
  `bank_name` VARCHAR(45) NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  `password` VARCHAR(65) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`card_number`),
  INDEX `idx_card_number` (`card_number` ASC) INVISIBLE,
  INDEX `fk_credit_card_person1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_person1`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`address` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `building_number` VARCHAR(45) NOT NULL,
  `room_number` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_address_country` (`country` ASC) INVISIBLE,
  INDEX `idx_address_city_street` (`city` ASC, `street` ASC) VISIBLE,
  INDEX `fk_address_person1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_person1`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`category` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`dish` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `title_ukr` VARCHAR(45) NOT NULL,
  `description` LONGTEXT NULL,
  `description_ukr` LONGTEXT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `weight` INT NOT NULL,
  `count` INT NOT NULL,
  `minutes_to_cook` INT NULL,
  `date_created` TIMESTAMP(2) NOT NULL,
  `image` BLOB NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT INDEX `idx_dish_title` (`title`, `title_ukr`) INVISIBLE,
  INDEX `idx_dish_category_count` (`count` ASC) INVISIBLE,
  INDEX `idx_dish_date_created` (`date_created` ASC) VISIBLE,
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE,
  UNIQUE INDEX `title_ukr_UNIQUE` (`title_ukr` ASC) VISIBLE,
  INDEX `fk_dish_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_dish_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `restaurant`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`receipt_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`receipt_status` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`receipt_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`receipt` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`receipt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `time_created` DATETIME NOT NULL,
  `discount` INT NULL,
  `total_price` DECIMAL(10,2) NOT NULL,
  `user_id` INT NOT NULL,
  `receipt_status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_time_created` (`time_created` ASC) INVISIBLE,
  INDEX `idx_price` (`total_price` ASC) VISIBLE,
  INDEX `fk_receipt_person1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_receipt_receipt_status1_idx` (`receipt_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_receipt_person1`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_receipt_receipt_status1`
    FOREIGN KEY (`receipt_status_id`)
    REFERENCES `restaurant`.`receipt_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`receipt_has_dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`receipt_has_dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`receipt_has_dish` (
  `receipt_id` INT NOT NULL,
  `dish_id` INT NOT NULL,
  PRIMARY KEY (`receipt_id`, `dish_id`),
  INDEX `fk_receipt_has_dish_dish1_idx` (`dish_id` ASC) VISIBLE,
  INDEX `fk_receipt_has_dish_receipt1_idx` (`receipt_id` ASC) VISIBLE,
  CONSTRAINT `fk_receipt_has_dish_receipt1`
    FOREIGN KEY (`receipt_id`)
    REFERENCES `restaurant`.`receipt` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_receipt_has_dish_dish1`
    FOREIGN KEY (`dish_id`)
    REFERENCES `restaurant`.`dish` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`status_flow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`status_flow` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`status_flow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `from` INT NOT NULL,
  `to` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_status_flow_receipt_status1_idx` (`from` ASC) VISIBLE,
  INDEX `fk_status_flow_receipt_status2_idx` (`to` ASC) VISIBLE,
  CONSTRAINT `fk_status_flow_from`
    FOREIGN KEY (`from`)
    REFERENCES `restaurant`.`receipt_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_status_flow_to`
    FOREIGN KEY (`to`)
    REFERENCES `restaurant`.`receipt_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ----------------------------------------------------------
-- Inserts
-- ----------------------------------------------------------
INSERT INTO role (id, name) VALUES 
										(DEFAULT, "Manager"),
										(DEFAULT, "Client"),
										(DEFAULT, "Unauthorized_user");

INSERT INTO category(id, name) VALUES 
										(DEFAULT, "Salad"),
										(DEFAULT, "Pizza"),
										(DEFAULT, "Appetizer"),
										(DEFAULT, "Drink");

INSERT INTO receipt_status(id, status)  VALUES 
										(DEFAULT, "New"),
										(DEFAULT, "Cooking"),
										(DEFAULT, "Delivery"),
										(DEFAULT, "Completed");


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
