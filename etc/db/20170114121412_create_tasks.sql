CREATE TABLE `tasks` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT,
  `checklist_id` INT unsigned NOT NULL,
  `description` varchar(30),
  `complete` BOOLEAN DEFAULT 0,
  `due_date` DATE,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `Index_tasks_due_date` (`due_date`),
  CONSTRAINT `FK_tasks_checklist` FOREIGN KEY `FK_tasks_checklist` (`checklist_id`) REFERENCES `checklists` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;