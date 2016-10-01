--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `database`.`achats` DROP FOREIGN KEY `achats_ibfk_2`;

ALTER TABLE `database`.`achats` DROP FOREIGN KEY `achats_ibfk_1`;

ALTER TABLE `database`.`achats` DROP PRIMARY KEY;

ALTER TABLE `database`.`application` DROP PRIMARY KEY;

ALTER TABLE `database`.`abonnement` DROP PRIMARY KEY;

ALTER TABLE `database`.`utilisateur` DROP PRIMARY KEY;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idApp_6`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idUtilisateur_3`;

ALTER TABLE `database`.`abonnement` DROP INDEX `database`.`idUtilisateur_2`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idApp_4`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idApp_2`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idApp_7`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idUtilisateur`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idUtilisateur_2`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idApp_5`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idUtilisateur`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idUtilisateur_3`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idUtilisateur_4`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idApp_3`;

ALTER TABLE `database`.`abonnement` DROP INDEX `database`.`idUtilisateur`;

ALTER TABLE `database`.`achats` DROP INDEX `database`.`idApp`;

DROP TABLE `database`.`utilisateur`;

DROP TABLE `database`.`abonnement`;

DROP TABLE `database`.`application`;

DROP TABLE `database`.`achats`;

CREATE TABLE `database`.`utilisateur` (
	`nom` VARCHAR(50) NOT NULL,
	`prenom` VARCHAR(50) NOT NULL,
	`idUtilisateur` INTEGER UNSIGNED NOT NULL,
	`password` TEXT NOT NULL,
	`alias` TEXT NOT NULL,
	`email` TEXT NOT NULL,
	`logo` VARCHAR(15) NOT NULL,
	PRIMARY KEY (`idUtilisateur`)
) ENGINE=InnoDB;

CREATE TABLE `database`.`abonnement` (
	`idAbonnement` INTEGER UNSIGNED NOT NULL,
	`idUtilisateur` INTEGER UNSIGNED NOT NULL,
	`datedesouscription` TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	`nom` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`idAbonnement`)
) ENGINE=InnoDB;

CREATE TABLE `database`.`application` (
	`idApplication` INT NOT NULL,
	`description` TEXT,
	`logo` VARCHAR(150),
	`code` VARCHAR(20) NOT NULL,
	`nom` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`idApplication`)
) ENGINE=InnoDB;

CREATE TABLE `database`.`achats` (
	`idUtilisateur` INTEGER UNSIGNED NOT NULL,
	`idApp` INTEGER UNSIGNED NOT NULL,
	`buyTime` TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	PRIMARY KEY (`idUtilisateur`,`idApp`)
) ENGINE=InnoDB;

CREATE INDEX `idApp_6` ON `database`.`achats` (`idApp` ASC);

CREATE UNIQUE INDEX `idUtilisateur_3` ON `database`.`achats` (`idApp` ASC);

CREATE INDEX `idUtilisateur_2` ON `database`.`abonnement` (`idUtilisateur` ASC);

CREATE UNIQUE INDEX `idApp_4` ON `database`.`achats` (`idApp` ASC);

CREATE UNIQUE INDEX `idUtilisateur_2` ON `database`.`achats` (`idUtilisateur` ASC);

CREATE INDEX `idApp_5` ON `database`.`achats` (`idApp` ASC);

CREATE INDEX `idUtilisateur` ON `database`.`achats` (`idUtilisateur` ASC);

CREATE INDEX `idUtilisateur_4` ON `database`.`achats` (`idUtilisateur` ASC);

CREATE UNIQUE INDEX `idUtilisateur_3` ON `database`.`achats` (`idUtilisateur` ASC);

CREATE UNIQUE INDEX `idApp_2` ON `database`.`achats` (`idApp` ASC);

CREATE INDEX `idApp_3` ON `database`.`achats` (`idApp` ASC);

CREATE INDEX `idApp_7` ON `database`.`achats` (`idApp` ASC);

CREATE INDEX `idUtilisateur` ON `database`.`achats` (`idApp` ASC);

CREATE INDEX `idUtilisateur` ON `database`.`abonnement` (`idUtilisateur` ASC);

CREATE INDEX `idApp` ON `database`.`achats` (`idApp` ASC);

ALTER TABLE `database`.`achats` ADD PRIMARY KEY (`idUtilisateur`, `idApp`);

ALTER TABLE `database`.`achats` ADD UNIQUE (`idApp`);

ALTER TABLE `database`.`abonnement` ADD PRIMARY KEY (`idAbonnement`);

ALTER TABLE `database`.`achats` ADD CONSTRAINT `achats_ibfk_2` FOREIGN KEY (`idApp`)
	REFERENCES `database`.`achats` (`idUtilisateur`, `idApp`);

ALTER TABLE `database`.`achats` ADD CONSTRAINT `achats_ibfk_1` FOREIGN KEY (`idUtilisateur`)
	REFERENCES `database`.`achats` (`idUtilisateur`, `idApp`);

