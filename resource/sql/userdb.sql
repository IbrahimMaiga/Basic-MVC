-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 15 Mars 2017 à 10:31
-- Version du serveur :  5.7.9
-- Version de PHP :  5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `userdb`
--

-- --------------------------------------------------------

--
-- Structure de la table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `job_id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(150) NOT NULL,
  `job_description` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `job`
--

INSERT INTO `job` (`job_id`, `job_name`, `job_description`) VALUES
  (1, 'Informaticien', 'Développeur d''application');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'User Id',
  `firstname` varchar(150) NOT NULL,
  `lastname` varchar(150) NOT NULL,
  `username` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_connection` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `online` tinyint(1) NOT NULL,
  `group_id` int(10) UNSIGNED NOT NULL,
  `job_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_us_gr` (`group_id`),
  KEY `fk_usersjob` (`job_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `username`, `password`, `last_connection`, `created`, `online`, `group_id`, `job_id`) VALUES
  (1, 'John', 'Doe', 'admin', '21232f297a57fa5fa743f894ae4af801ffc3', '2017-02-15 11:23:00', '2017-02-15 11:44:29', 0, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE IF NOT EXISTS `user_group` (
  `gr_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'User Group Id',
  `gr_name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `gr_description` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`gr_id`),
  UNIQUE KEY `gr_unique` (`gr_name`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user_group`
--

INSERT INTO `user_group` (`gr_id`, `gr_name`, `gr_description`) VALUES
  (1, 'admin', 'Privilège Admin (Administrateur)');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
