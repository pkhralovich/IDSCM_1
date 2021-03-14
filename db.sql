
CREATE TABLE `heroku_bc706673f184f21`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45),
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email` (`email` ASC) VISIBLE);

CREATE TABLE `heroku_bc706673f184f21`.`videos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `author` VARCHAR(45) NULL,
  `creation_date` DATETIME NOT NULL,
  `duration` INT NOT NULL DEFAULT 0,
  `plays` INT NOT NULL DEFAULT 0,
  `description` VARCHAR(45) NOT NULL,
  `format` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));