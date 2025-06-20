-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 20 juin 2025 à 17:01
-- Version du serveur :  10.4.18-MariaDB
-- Version de PHP : 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `doctor-ms-db`
--

-- --------------------------------------------------------

--
-- Structure de la table `doctor`
--

CREATE TABLE `doctor` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `endTime` time(6) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `startTime` time(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `doctor`
--

INSERT INTO `doctor` (`id`, `email`, `endTime`, `firstName`, `lastName`, `phone`, `specialization`, `startTime`) VALUES
(3, 'hanane@gmail.com', '14:44:00.000000', 'hanane', 'lahssini', '0612457896', 'tst', '14:44:00.000000'),
(4, 'Zaineb@gmail.com', '15:11:00.000000', 'Zaineb', 'Belaid', '0612457896', 'Cardiologist', '15:07:00.000000');

-- --------------------------------------------------------

--
-- Structure de la table `doctor_availabledays`
--

CREATE TABLE `doctor_availabledays` (
  `Doctor_id` bigint(20) NOT NULL,
  `availableDays` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `doctor_availabledays`
--

INSERT INTO `doctor_availabledays` (`Doctor_id`, `availableDays`) VALUES
(3, 1),
(4, 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `doctor_availabledays`
--
ALTER TABLE `doctor_availabledays`
  ADD KEY `FKck3m76620tcjfaft0pdp44wue` (`Doctor_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `doctor`
--
ALTER TABLE `doctor`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `doctor_availabledays`
--
ALTER TABLE `doctor_availabledays`
  ADD CONSTRAINT `FKck3m76620tcjfaft0pdp44wue` FOREIGN KEY (`Doctor_id`) REFERENCES `doctor` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
