-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 11 juil. 2025 à 18:20
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
-- Base de données : `clinicidentity`
--

-- --------------------------------------------------------

--
-- Structure de la table `menuitem`
--

CREATE TABLE `menuitem` (
  `id` bigint(20) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `display_order` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `menuitem`
--

INSERT INTO `menuitem` (`id`, `icon`, `path`, `title`, `display_order`) VALUES
(1, 'flaticon-menu-1', '/dashboard', 'dashboard', 1),
(2, 'flaticon-calendar', '/appointments', 'appointments', 2),
(3, 'flaticon-user-1', '/patients', 'patients', 3),
(4, 'flaticon-user', '/doctors', 'doctors', 4),
(7, 'flaticon-user-1', '/users', 'users', 7);

-- --------------------------------------------------------

--
-- Structure de la table `menu_roles`
--

CREATE TABLE `menu_roles` (
  `role_id` int(11) NOT NULL,
  `menu_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `menu_roles`
--

INSERT INTO `menu_roles` (`role_id`, `menu_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'admin'),
(2, 'doctor'),
(3, 'patient');

-- --------------------------------------------------------

--
-- Structure de la table `role_routes`
--

CREATE TABLE `role_routes` (
  `role_id` int(11) NOT NULL,
  `route_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `usercredential`
--

CREATE TABLE `usercredential` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `resetToken` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `patientId` bigint(20) DEFAULT NULL,
  `doctorId` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `usercredential`
--

INSERT INTO `usercredential` (`id`, `email`, `firstName`, `lastName`, `password`, `phone`, `resetToken`, `deleted`, `patientId`, `doctorId`) VALUES
(1, 'janajonita@gmail.com', 'Hanane', 'Lahssini', '$2a$10$PNKFbdJrLFPfTFTj.BR8kugOdfabhTssKpBR/SBWv2aMlLnxAnO6q', '0623569674', NULL, 0, NULL, NULL),
(2, 'system@internal.local', 'System', 'Internal', '$2a$10$AbcdEFGHijklMNOPqrstuvHASHEDPWD', '0000000000', NULL, 1, NULL, NULL),
(3, 'janat@gmail.com', 'janat', 'janat', '$2a$10$CKh1IEi9W5Q0o7FnkUee4u59aY/DP/JH.B1QClhMEHtYawhR/uwoK', '0612457896', NULL, 0, NULL, 1),
(4, 'sara@gmail.com', 'sara', 'sara', '$2a$10$wIu.TwZERj9L9xSuQu5tlO.x/ND.lT9JaYvw8M.qv2P9/Vum2ZjyC', '0612457896', NULL, 0, 21, NULL),
(5, 'soufiane@gmail.com', 'soufiane', 'sajid', '$2a$10$mzQ1JEVYJtsGe0El9/v8quJ/CUev.rDtqKvLKQSyMo9OkuuvNBtUe', '0612457896', NULL, 0, NULL, 3);

-- --------------------------------------------------------

--
-- Structure de la table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(3, 2),
(4, 3),
(5, 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `menuitem`
--
ALTER TABLE `menuitem`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `menu_roles`
--
ALTER TABLE `menu_roles`
  ADD PRIMARY KEY (`role_id`,`menu_id`),
  ADD KEY `FK27wi8fb0pv1xw886c6ab7vj2w` (`menu_id`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `role_routes`
--
ALTER TABLE `role_routes`
  ADD PRIMARY KEY (`role_id`,`route_id`),
  ADD KEY `FKf17aq18bcfdwqraedqs7llcgm` (`route_id`);

--
-- Index pour la table `usercredential`
--
ALTER TABLE `usercredential`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKbhgxpici80n5kpvs65q90ou14` (`role_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `menuitem`
--
ALTER TABLE `menuitem`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `usercredential`
--
ALTER TABLE `usercredential`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
