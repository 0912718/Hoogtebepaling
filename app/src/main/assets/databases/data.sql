BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `bssid` (
	`bssidName`	TEXT
);
INSERT INTO `bssid` VALUES ('testRouter1');
INSERT INTO `bssid` VALUES ('testRouter2');
COMMIT;
