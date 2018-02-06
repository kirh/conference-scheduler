-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema conference_scheduler
-- -----------------------------------------------------
-- Администратор создает конференцию, ее тематические секции.
-- Участник регистрируется и подает заявки на участие в секции.
-- Администратор проверяет заявку на соответствие тематике секции и
-- подтверждает или отклоняет заявку.
-- Участник может снять заявку, задать вопрос администратору.

-- -----------------------------------------------------
-- Schema conference_scheduler
--
-- Администратор создает конференцию, ее тематические секции.
-- Участник регистрируется и подает заявки на участие в секции.
-- Администратор проверяет заявку на соответствие тематике секции и
-- подтверждает или отклоняет заявку.
-- Участник может снять заявку, задать вопрос администратору.
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `conference_scheduler` DEFAULT CHARACTER SET utf8 ;
USE `conference_scheduler` ;

-- -----------------------------------------------------
-- Table `conference_scheduler`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference_scheduler`.`user` (
  `u_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  `u_username` VARCHAR(16) NOT NULL,
  `u_password` CHAR(32) NOT NULL COMMENT 'Хэш пароля',
  `u_email` VARCHAR(255) NOT NULL,
  `u_first_name` VARCHAR(45) NOT NULL COMMENT 'Имя',
  `u_last_name` VARCHAR(45) NOT NULL COMMENT 'Фамилия',
  `u_is_admin` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Является ли администратором',
  PRIMARY KEY (`u_id`),
  UNIQUE INDEX `username_UNIQUE` (`u_username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Пользователь. Так как присутствует всего 2 роли и нет информации о возможном добавлении ролей в будущем\nпросто добавил атрибут is_admin. Проверка при создании конференции, заявки, вопроса осуществляется тригерром.';


-- -----------------------------------------------------
-- Table `conference_scheduler`.`conference`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference_scheduler`.`conference` (
  `c_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор конференции',
  `c_name` VARCHAR(255) NOT NULL COMMENT 'Название конференции\nУникально',
  `c_description` TEXT NOT NULL,
  `c_address` VARCHAR(255) NULL COMMENT 'Адрес проведения\nМожет быть неизвестен заранее',
  `c_date` DATE NULL COMMENT 'Время проведения\nМожет быть неизвестно заранее',
  `c_u_id` INT UNSIGNED NOT NULL COMMENT 'Администратор\nПроверка триггером',
  PRIMARY KEY (`c_id`),
  INDEX `fk_conference_user1_idx` (`c_u_id` ASC),
  UNIQUE INDEX `c_name_UNIQUE` (`c_name` ASC)  COMMENT 'Название конференции должно быть уникально',
  CONSTRAINT `fk_conference_user1`
    FOREIGN KEY (`c_u_id`)
    REFERENCES `conference_scheduler`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Конференция.'
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `conference_scheduler`.`section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference_scheduler`.`section` (
  `s_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  `s_topic` VARCHAR(255) NOT NULL COMMENT 'Тема секции',
  `s_c_id` INT UNSIGNED NOT NULL COMMENT 'Конференция к которой секция относится',
  PRIMARY KEY (`s_id`),
  INDEX `fk_section_conferences_idx` (`s_c_id` ASC),
  UNIQUE INDEX `s_topic_and_s_c_id_UNIQUE` (`s_c_id` ASC, `s_topic` ASC)  COMMENT 'Темы секции в рамках одной конференции должны быть уникальны',
  CONSTRAINT `fk_section_conferences`
    FOREIGN KEY (`s_c_id`)
    REFERENCES `conference_scheduler`.`conference` (`c_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Секция конференции, представляет тематический подраздел.';


-- -----------------------------------------------------
-- Table `conference_scheduler`.`proposal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference_scheduler`.`proposal` (
  `p_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  `p_title` VARCHAR(255) NOT NULL COMMENT 'Название доклада',
  `p_description` TEXT NOT NULL COMMENT 'Описание доклада',
  `p_s_id` INT UNSIGNED NOT NULL COMMENT 'Секция конференции в которую подана заявка',
  `p_u_id` INT UNSIGNED NOT NULL COMMENT 'Участник отправивший заявку\nНе может быть администратором.\nПроверка триггером',
  `p_status` ENUM('PENDING', 'REJECTED', 'APPROVED') NOT NULL DEFAULT 'PENDING' COMMENT 'Статус заявки',
  PRIMARY KEY (`p_id`),
  INDEX `fk_proposal_section1_idx` (`p_s_id` ASC),
  INDEX `fk_proposal_user1_idx` (`p_u_id` ASC),
  CONSTRAINT `fk_proposal_section1`
    FOREIGN KEY (`p_s_id`)
    REFERENCES `conference_scheduler`.`section` (`s_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_proposal_user1`
    FOREIGN KEY (`p_u_id`)
    REFERENCES `conference_scheduler`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Заявка на участие в конференции';


-- -----------------------------------------------------
-- Table `conference_scheduler`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference_scheduler`.`question` (
  `q_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  `q_text` TEXT NOT NULL COMMENT 'Вопрос',
  `q_u_id` INT UNSIGNED NOT NULL COMMENT 'Участник задавший вопрос\nПроверка триггером. Администратор\nне может задать вопрос',
  `q_c_id` INT UNSIGNED NOT NULL COMMENT 'Конференция по которой задан вопрос',
  PRIMARY KEY (`q_id`),
  INDEX `fk_question_participant1_idx` (`q_u_id` ASC),
  INDEX `fk_question_conference1_idx` (`q_c_id` ASC),
  CONSTRAINT `fk_question_user1`
    FOREIGN KEY (`q_u_id`)
    REFERENCES `conference_scheduler`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_question_conference1`
    FOREIGN KEY (`q_c_id`)
    REFERENCES `conference_scheduler`.`conference` (`c_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Вопрос заданный участником администратору конференции. Сделал связь через таблицу conference т.к. администратор может управлять\nнесколькими конференциями и необходимо знать к какой конкретно конференции относится вопрос.';


-- -----------------------------------------------------
-- Table `conference_scheduler`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference_scheduler`.`message` (
  `m_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  `m_text` TEXT NOT NULL COMMENT 'Текст сообщения',
  `m_q_id` INT UNSIGNED NOT NULL COMMENT 'Вопрос к которому это сообщение относится',
  `m_u_id` INT UNSIGNED NOT NULL COMMENT 'Пользователь отправивший сообщение',
  `m_create_time` TIMESTAMP NOT NULL COMMENT 'Время создания сообщения.\nЗадается триггером.',
  PRIMARY KEY (`m_id`),
  INDEX `fk_message_question1_idx` (`m_q_id` ASC),
  INDEX `fk_message_participant1_idx` (`m_u_id` ASC),
  CONSTRAINT `fk_message_question1`
    FOREIGN KEY (`m_q_id`)
    REFERENCES `conference_scheduler`.`question` (`q_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`m_u_id`)
    REFERENCES `conference_scheduler`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Сообщение в рамках вопроса по конференции';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
USE `conference_scheduler`;

DELIMITER $$
USE `conference_scheduler`$$
CREATE TRIGGER `conference_scheduler`.`c_permit_insert_when_user_is_admin` BEFORE INSERT ON `conference` FOR EACH ROW
BEGIN
 DECLARE is_admin Bool;
 SELECT `u_is_admin`
 INTO is_admin
 FROM `user`
 WHERE `u_id`= NEW.`c_u_id`;
 IF is_admin = false THEN
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Only administrator can create conference', MYSQL_ERRNO = 1001;
 END IF;
END$$

USE `conference_scheduler`$$
CREATE DEFINER = CURRENT_USER TRIGGER `conference_scheduler`.`p_permit_insert_when_user_is_participant` BEFORE INSERT ON `proposal` FOR EACH ROW
BEGIN
	DECLARE is_admin Bool;
	SELECT `u_is_admin`
	INTO is_admin
    FROM `user`
	WHERE `u_id` = NEW.`p_u_id`;
	IF is_admin = true THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Only participant can create proposal', MYSQL_ERRNO = 1001;
	END IF;
END$$

USE `conference_scheduler`$$
CREATE DEFINER = CURRENT_USER TRIGGER `conference_scheduler`.`q_permit_insert_when_user_is_participant` BEFORE INSERT ON `question` FOR EACH ROW
BEGIN
	DECLARE is_admin Bool;
	SELECT `u_is_admin`
	INTO is_admin
    FROM `user`
	WHERE `u_id` = NEW.`q_u_id`;
	IF is_admin = true THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Only participant can create question', MYSQL_ERRNO = 1001;
	END IF;
END$$

USE `conference_scheduler`$$
CREATE
DEFINER = CURRENT_USER
TRIGGER `conference_scheduler`.`m_set_time_BEFORE_INSERT`
BEFORE INSERT ON `message` FOR EACH ROW
SET NEW.`m_create_time` = NOW();$$


DELIMITER ;
-- begin attached script 'script'
#User
INSERT INTO `conference_scheduler`.`user` (`u_id`, `u_username`, `u_password`, `u_email`, `u_first_name`, `u_last_name`, `u_is_admin`) VALUES ('1', 'admin', md5('Admin91'), 'admin@mail.by', 'Ivan', 'Ivanov', '1');
INSERT INTO `conference_scheduler`.`user` (`u_id`, `u_username`, `u_password`, `u_email`, `u_first_name`, `u_last_name`, `u_is_admin`) VALUES ('2', 'kirh91', md5('Password91'), 'kirh@mail.by', 'Kirill', 'Hilman', '1');
INSERT INTO `conference_scheduler`.`user` (`u_id`, `u_username`, `u_password`, `u_email`, `u_first_name`, `u_last_name`, `u_is_admin`) VALUES ('3', 'vasya', md5('Vasya11'), 'vasya@mail.by', 'Vasya', 'Vasilevsky', '1');
INSERT INTO `conference_scheduler`.`user` (`u_id`, `u_username`, `u_password`, `u_email`, `u_first_name`, `u_last_name`, `u_is_admin`) VALUES ('4', 'jekaB', md5('Jeka11'), 'jeka@mail.ru', 'Evgeniy	', 'Borisov', '0');
INSERT INTO `conference_scheduler`.`user` (`u_id`, `u_username`, `u_password`, `u_email`, `u_first_name`, `u_last_name`, `u_is_admin`) VALUES ('5', 'tolkachev', md5('Pasword11'), 'tolkachev@mail.ru', 'Kirill', 'Tolkachev', '0');
INSERT INTO `conference_scheduler`.`user` (`u_id`, `u_username`, `u_password`, `u_email`, `u_first_name`, `u_last_name`, `u_is_admin`) VALUES ('6', 'baruh', md5('Baruh11'), 'baruh@domain.ru', 'Baruh', 'Sadgurskiy', '0');
INSERT INTO `conference_scheduler`.`user` (`u_id`, `u_username`, `u_password`, `u_email`, `u_first_name`, `u_last_name`, `u_is_admin`) VALUES ('7', 'fainJ', md5('Fain11'), 'fain@gmail.com', 'Jakov', 'Fain', '0');

#Conference
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('1', 'javaone', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'San Francisco', '2018-10-01 10:00:00', '2');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('2', 'joker', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'Moscow', '2018-11-01 10:00:00', '1');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('3', 'jbreak', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'Novosibirsk', '2018-09-05 10:00:00', '3');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('4', 'Деловой Интернет', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'Минск', '2018-02-09 10:00:00', '1');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('5', 'QCon', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'London', '2018-02-09 10:00:00', '1');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('6', 'Spring I/O', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'Berlin', '2018-03-09 10:00:00', '2');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('7', 'DevDays', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'Vilnus', '2018-04-09 10:00:00', '3');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('8', 'Riga Dev Days', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'Riga', '2018-04-09 10:00:00', '1');
INSERT INTO `conference_scheduler`.`conference` (`c_id`, `c_name`, `c_description`, `c_address`, `c_date`, `c_u_id`) VALUES ('9', 'Voxxed Days Bucharest', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.
Donec vel egestas justo, consequat convallis nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean leo ante, rutrum at lacus et, dapibus bibendum magna. Quisque a tempus erat. Suspendisse nec semper ex. Nulla maximus ultrices metus et mattis. Suspendisse eu feugiat lectus. Cras felis magna, maximus in varius sit amet, suscipit vitae orci. In id ipsum sed elit dictum rhoncus. In tincidunt, risus quis porta maximus, justo urna tempus augue, sed finibus metus erat id augue. Nam pellentesque gravida luctus. Quisque quis ex scelerisque, imperdiet ex eget, convallis sem. Suspendisse et porttitor tellus, rutrum congue nunc. Praesent elementum enim id semper sagittis. Ut at iaculis felis, sed eleifend lectus.', 'Bucharest', '2018-05-09 10:00:00', '2');

#Sections
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('1', 'Cloud', '1');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('3', 'Multithreading', '2');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('4', 'ORM', '2');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('2', 'Spring', '2');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('5', 'Big data', '3');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('6', 'Profiling', '3');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('7', 'Distributed systems', '4');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('8', 'Java 9 new features', '4');
INSERT INTO `conference_scheduler`.`section` (`s_id`, `s_topic`, `s_c_id`) VALUES ('9', 'Containers', '5');


#Proposals
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('1', 'Spring Boot 2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '2', '4', 'APPROVED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('2', 'Spring Web Flux', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '2', '5', 'APPROVED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('3', 'Spring Web MVC Framework', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '2', '6', 'PENDING');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('6', 'Profilers', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '6', '6', 'REJECTED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('7', 'Cloud services', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '1', '7', 'REJECTED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('8', 'Docker in production', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '9', '4', 'REJECTED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('9', 'Hibernate', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '4', '5', 'APPROVED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('10', 'Hadoop', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '5', '6', 'APPROVED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`, `p_status`) VALUES ('11', 'Spark', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '5', '6', 'APPROVED');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`) VALUES ('4', 'Concurrent tools', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '3', '4');
INSERT INTO `conference_scheduler`.`proposal` (`p_id`, `p_title`, `p_description`, `p_s_id`, `p_u_id`) VALUES ('5', 'Java Modules', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque congue, ex sed molestie auctor, libero lacus tincidunt lectus, at sodales lacus arcu et nibh. Donec facilisis lacinia finibus. Aliquam aliquet non elit eget laoreet. Phasellus non sollicitudin massa, eget efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hendrerit, sem sit amet interdum venenatis, metus orci blandit erat, ac congue ipsum tellus commodo justo. Maecenas rhoncus sollicitudin tristique. Sed congue ornare ex sed lobortis. Ut mi eros, commodo id odio ut, egestas varius orci. Morbi imperdiet luctus metus id maximus. Nunc volutpat eget felis eu condimentum.', '8', '5');

#Questions
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('1', 'Interdum et malesuada fames ac ante ipsum primis in faucibus.', '4', '4');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('2', 'Sed lobortis varius tempus.', '5', '4');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('3', 'Sed vulputate laoreet leo sit amet fringilla. ', '6', '2');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('4', 'Fusce nec sem venenatis, sagittis libero et, eleifend tortor.', '7', '3');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('5', 'Nullam erat lorem, rhoncus at rhoncus in, fermentum vitae sem. ', '4', '5');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('6', 'Suspendisse varius bibendum tortor ac vestibulum. ', '5', '6');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('7', 'Ut hendrerit orci eget lectus maximus euismod.', '6', '5');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('8', 'Cras laoreet velit in consequat hendrerit.', '7', '2');
INSERT INTO `conference_scheduler`.`question` (`q_id`, `q_text`, `q_u_id`, `q_c_id`) VALUES ('9', 'Suspendisse rhoncus velit id ligula posuere', '4', '3');

#Messages
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('1', 'Fusce sit amet accumsan purus. Sed tempor ut ex non auctor.', '1', '4');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('2', 'In pellentesque nisi ipsum, sit amet blandit neque dictum ac.', '2', '5');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('3', 'Aliquam ante nunc, faucibus vitae ante in, semper bibendum nisi.', '3', '6');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('4', 'Phasellus eu libero diam. Praesent fermentum mattis tortor.', '4', '7');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('5', 'Sed faucibus odio ex, non consequat enim sodales quis. Nam at condimentum metus.', '1', '4');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('6', 'Sed vel ex mollis, euismod libero ut, vehicula nibh.', '2', '5');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('7', 'Donec placerat porttitor metus. Nam ut quam diam. ', '3', '6');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('8', 'Etiam vehicula scelerisque neque, non vehicula diam consectetur at.', '4', '7');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('9', 'Curabitur diam ipsum, pulvinar ac est a, vulputate pulvinar dui.', '1', '4');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('10', 'Nulla sit amet sollicitudin justo, et gravida tortor. ', '2', '5');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('11', 'In hac habitasse platea dictumst. Aenean tristique nisi sit amet rutrum pellentesque.', '1', '2');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('12', 'Nunc faucibus vehicula laoreet. Proin porttitor turpis et felis mattis, vitae scelerisque sem dignissim.', '2', '5');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('13', 'Donec aliquam orci vel efficitur pretium. Donec in metus lectus. ', '3', '6');
INSERT INTO `conference_scheduler`.`message` (`m_id`, `m_text`, `m_q_id`, `m_u_id`) VALUES ('14', 'Fusce quis elit pellentesque, pellentesque odio id, sollicitudin nibh.', '4', '7');

-- end attached script 'script'
