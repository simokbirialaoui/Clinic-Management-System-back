-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 23 juil. 2025 à 18:13
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

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
CREATE DATABASE IF NOT EXISTS `doctor-ms-db`;
USE `doctor-ms-db`;

CREATE TABLE `doctor` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `endTime` time(6) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `startTime` time(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `doctor`
--

INSERT INTO `doctor` (`id`, `email`, `endTime`, `firstName`, `lastName`, `phone`, `specialization`, `startTime`) VALUES
(1, 'janat@gmail.com', NULL, 'janat', 'lahssini', '0612459641', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `doctor_availabledays`
--

CREATE TABLE `doctor_availabledays` (
  `Doctor_id` bigint(20) NOT NULL,
  `availableDays` tinyint(4) DEFAULT NULL,
  `available_day` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
