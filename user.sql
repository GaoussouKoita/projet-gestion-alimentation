CREATE USER 'dba_alimentation'@'%' IDENTIFIED BY '';
SET Password for 'dba_alimentation'@'%' = PASSWORD('_zcfWJV2XND*N-dU');
GRANT USAGE ON *.* TO 'dba_alimentation'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
CREATE DATABASE IF NOT EXISTS `dba_alimentation`;
GRANT ALL PRIVILEGES ON `dba_alimentation`.* TO 'dba_alimentation'@'%';