--Avant de demarrer l'application
CREATE USER 'dba_alimentation'@'%' IDENTIFIED BY '';
SET Password for 'dba_alimentation'@'%' = PASSWORD('_zcfWJV2XND*N-dU');
GRANT USAGE ON *.* TO 'dba_alimentation'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
CREATE DATABASE IF NOT EXISTS `dba_alimentation`;
GRANT ALL PRIVILEGES ON `dba_alimentation`.* TO 'dba_alimentation'@'%';



--Apres le demarrage de l'application
INSERT INTO `role` (`id`, `role_name`) VALUES
(1, 'ADMINISTRATEUR'), (2, 'UTILISATEUR');


--identifiant: admin@g:1234
INSERT INTO `utilisateur` (`id`, `adresse`, `email`, `nom`, `password`, `prenom`, `telephone`) VALUES
(1, 'Baguineda', 'admin@g', 'KOITA', '$2a$10$Je6x7uIYNLGUnIX6TzPWsekfBNLQEmvPC9hsCTyN56jPVkNzx/Rn.', 'Gaoussou', 76684788);


INSERT INTO `utilisateur_roles` (`utilisateur_id`, `roles_id`) VALUES
(1, 1), (1, 2);