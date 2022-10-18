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
-- Table `restaurant`.`contacts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`contacts` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`contacts` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `country` VARCHAR(45) NOT NULL,
                                                       `city` VARCHAR(45) NOT NULL,
                                                       `street` VARCHAR(45) NOT NULL,
                                                       `building` VARCHAR(45) NOT NULL,
                                                       `phone` VARCHAR(45) NOT NULL,
                                                       PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE INDEX `idx_address_country` ON `restaurant`.`contacts` (`country` ASC) INVISIBLE;

CREATE INDEX `idx_address_city_street` ON `restaurant`.`contacts` (`city` ASC, `street` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`category` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`category` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `name` VARCHAR(45) NOT NULL,
                                                       PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `restaurant`.`category` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`dish` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `title` VARCHAR(45) NOT NULL,
                                                   `description` VARCHAR(255) NULL,
                                                   `price` DECIMAL(10,2) NOT NULL,
                                                   `weight` INT NOT NULL,
                                                   `cooking` INT NULL,
                                                   `created` TIMESTAMP(2) NOT NULL,
                                                   `category_id` INT NOT NULL,
                                                   PRIMARY KEY (`id`),
                                                   CONSTRAINT `fk_dish_category1`
                                                       FOREIGN KEY (`category_id`)
                                                           REFERENCES `restaurant`.`category` (`id`)
                                                           ON DELETE CASCADE
                                                           ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE FULLTEXT INDEX `idx_dish_title` ON `restaurant`.`dish` (`title`) INVISIBLE;

CREATE INDEX `idx_dish_created` ON `restaurant`.`dish` (`created` ASC) VISIBLE;

CREATE UNIQUE INDEX `title_UNIQUE` ON `restaurant`.`dish` (`title` ASC) VISIBLE;

CREATE INDEX `fk_dish_category1_idx` ON `restaurant`.`dish` (`category_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`role` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`role` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `name` VARCHAR(45) NOT NULL,
                                                   PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `restaurant`.`role` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`user` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`user` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `email` VARCHAR(45) NOT NULL,
                                                   `first_name` VARCHAR(45) NOT NULL,
                                                   `last_name` VARCHAR(45) NOT NULL,
                                                   `password` VARCHAR(65) NOT NULL,
                                                   `role_id` INT NOT NULL,
                                                   PRIMARY KEY (`id`),
                                                   CONSTRAINT `fk_person_role1`
                                                       FOREIGN KEY (`role_id`)
                                                           REFERENCES `restaurant`.`role` (`id`)
                                                           ON DELETE CASCADE
                                                           ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `restaurant`.`user` (`email` ASC) INVISIBLE;

CREATE INDEX `idx_first_name_last_name` ON `restaurant`.`user` (`first_name` ASC, `last_name` ASC) INVISIBLE;

CREATE INDEX `fk_person_role1_idx` ON `restaurant`.`user` (`role_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`receipt_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`receipt_status` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`receipt_status` (
                                                             `id` INT NOT NULL AUTO_INCREMENT,
                                                             `status` VARCHAR(45) NOT NULL,
                                                             PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE UNIQUE INDEX `status_UNIQUE` ON `restaurant`.`receipt_status` (`status` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`receipt` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`receipt` (
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `created` TIMESTAMP(2) NOT NULL,
                                                      `receipt_price` DECIMAL(10,2) NOT NULL,
                                                      `user_id` INT NOT NULL,
                                                      `receipt_status_id` INT NOT NULL,
                                                      `contacts_id` INT NOT NULL,
                                                      PRIMARY KEY (`id`, `contacts_id`),
                                                      CONSTRAINT `fk_receipt_person1`
                                                          FOREIGN KEY (`user_id`)
                                                              REFERENCES `restaurant`.`user` (`id`)
                                                              ON DELETE CASCADE
                                                              ON UPDATE CASCADE,
                                                      CONSTRAINT `fk_receipt_receipt_status1`
                                                          FOREIGN KEY (`receipt_status_id`)
                                                              REFERENCES `restaurant`.`receipt_status` (`id`)
                                                              ON DELETE NO ACTION
                                                              ON UPDATE NO ACTION,
                                                      CONSTRAINT `fk_receipt_address1`
                                                          FOREIGN KEY (`contacts_id`)
                                                              REFERENCES `restaurant`.`contacts` (`id`)
                                                              ON DELETE NO ACTION
                                                              ON UPDATE NO ACTION)
    ENGINE = InnoDB;

CREATE INDEX `idx_time_created` ON `restaurant`.`receipt` (`created` ASC) INVISIBLE;

CREATE INDEX `fk_receipt_person1_idx` ON `restaurant`.`receipt` (`user_id` ASC) VISIBLE;

CREATE INDEX `fk_receipt_receipt_status1_idx` ON `restaurant`.`receipt` (`receipt_status_id` ASC) VISIBLE;

CREATE INDEX `fk_receipt_address1_idx` ON `restaurant`.`receipt` (`contacts_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`receipt_has_dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`receipt_has_dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`receipt_has_dish` (
                                                               `receipt_id` INT NOT NULL,
                                                               `dish_id` INT NOT NULL,
                                                               `total_price` DECIMAL(10,2) NOT NULL,
                                                               `count` INT NOT NULL,
                                                               PRIMARY KEY (`receipt_id`, `dish_id`),
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

CREATE INDEX `fk_receipt_has_dish_dish1_idx` ON `restaurant`.`receipt_has_dish` (`dish_id` ASC) VISIBLE;

CREATE INDEX `fk_receipt_has_dish_receipt1_idx` ON `restaurant`.`receipt_has_dish` (`receipt_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `restaurant`.`status_flow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`status_flow` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`status_flow` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `from` INT NOT NULL,
                                                          `to` INT NOT NULL,
                                                          PRIMARY KEY (`id`),
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

CREATE INDEX `fk_status_flow_receipt_status1_idx` ON `restaurant`.`status_flow` (`from` ASC) VISIBLE;

CREATE INDEX `fk_status_flow_receipt_status2_idx` ON `restaurant`.`status_flow` (`to` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;





INSERT INTO role (id, name)
VALUES (DEFAULT, 'Client'),
       (DEFAULT, 'Manager');

INSERT INTO category(id, name)
VALUES (DEFAULT, 'Salad'),
       (DEFAULT, 'Pizza'),
       (DEFAULT, 'Appetizer'),
       (DEFAULT, 'Drink');

INSERT INTO receipt_status(id, status)
VALUES (DEFAULT, 'New'),
       (DEFAULT, 'Cooking'),
       (DEFAULT, 'Delivery'),
       (DEFAULT, 'Completed');

INSERT INTO status_flow(id, `from`, `to`)
VALUES (DEFAULT, 1, 2),
       (DEFAULT, 2, 3),
       (DEFAULT, 3, 4);

INSERT INTO user(id, email, first_name, last_name, password, role_id)
VALUES (DEFAULT, 'admin@admin.app', 'Admin', 'Admin', 'd9bbfb3d7aa0fcbd61cb0cfe606f80e444a36a0c0e62a54265255f54145545fb', 2),
       (DEFAULT, 'user@user.app', 'John', 'Doe', 'd9bbfb3d7aa0fcbd61cb0cfe606f80e444a36a0c0e62a54265255f54145545fb', 1);

INSERT INTO contacts(id, country, city, street, building, phone)
VALUES (DEFAULT, 'USA', 'Seattle', 'Washington', '1111', '380111111111'),
       (DEFAULT, 'USA', 'Washington', '1st', '84', '380111111112'),
       (DEFAULT, 'Canada', 'Toronto', 'Queen Elizabeth', '1', '380111111111'),
       (DEFAULT, 'USA', 'New Jersey', 'Bank', '11/45', '380111111115');

INSERT INTO dish(id, title, description, price, weight, cooking, created, category_id)
VALUES (DEFAULT, 'Diabola', 'Pizza with hottest sauce in Galaxy', 20.00, 900, 40, '2012-06-18 10:34:09', 2),
       (DEFAULT, 'Маргарита', 'М`ягкий смак і нічого зайвого', 30.00, 1000, 40, '2012-06-18 10:34:09', 2),
       (DEFAULT, 'Pepperoni', 'Like diabola, but more taste', 25.00, 400, 40, '2012-06-18 10:34:09', 2),
       (DEFAULT, 'Капричоза', 'Мікс на любий смак', 22.00, 900, 40, '2012-06-18 10:34:09', 2),
       (DEFAULT, 'Caesario', 'True satisfaction', 20.00, 900, 40, '2012-06-18 10:34:09', 2),
       (DEFAULT, 'Крабовий', 'На основі дійтичного соусу', 10.00, 200, 50, '2012-06-18 10:34:09', 1),
       (DEFAULT, 'Special', 'Our family receipt for you', 10.00, 200, 50, '2012-06-18 10:34:09', 1),
       (DEFAULT, 'Легкий', 'Червона риба нікого не залишить байдужим', 10.00, 200, 50, '2012-06-18 10:34:09', 1),
       (DEFAULT, 'Caesar', 'Want to fight with enemies?', 10.00, 200, 50, '2012-06-18 10:34:09', 1),
       (DEFAULT, 'Просто салат', 'Опис простого салату', 10.00, 200, 50, '2012-06-18 10:34:09', 1),
       (DEFAULT, 'Appetizer', 'Delicious', 15.00, 300, 10, '2012-06-18 10:34:09', 3),
       (DEFAULT, 'Appetizer2', 'Delicious', 15.00, 300, 10, '2012-06-18 10:34:09', 3),
       (DEFAULT, 'Appetizer3', 'Delicious', 25.00, 300, 10, '2012-06-18 10:34:09', 3),
       (DEFAULT, 'Appetizer4', 'Delicious', 19.00, 300, 10, '2012-06-18 10:34:09', 3),
       (DEFAULT, 'Appetizer5', 'Delicious', 5.00, 300, 10, '2012-06-18 10:34:09', 3),
       (DEFAULT, 'Light cocktail', 'Fresh strawberries with light drink', 90.00, 750, 0, '2012-06-18 10:34:09', 4),
       (DEFAULT, 'Фірмовий самогон', '10 років витримки в дубових бочка. Оригінальний рецепт', 190.00, 750, 0, '2012-06-18 10:34:09', 4),
       (DEFAULT, 'Chivas Regal', 'Old good whiskey', 290.00, 750, 0, '2012-06-18 10:34:09', 4),
       (DEFAULT, 'Давай знайомитись', 'Різноманіття смаку яке дозволить вам розслабитись', 390.00, 750, 0, '2012-06-18 10:34:09', 4),
       (DEFAULT, 'Beer', 'Just take one and smoke cigarettes on the bar', 490.00, 750, 0, '2012-06-18 10:34:09', 4);


INSERT INTO receipt (id, created, receipt_price, user_id, receipt_status_id, contacts_id)
VALUES (DEFAULT, '2012-06-18 10:34:09', 0, 2, 2, 1),
       (DEFAULT, '2012-05-18 10:34:09', 0, 2, 2, 1),
       (DEFAULT, '2012-04-18 10:34:09', 0, 2, 2, 3),
       (DEFAULT, '2012-03-18 10:34:09', 0, 2, 2, 4);
