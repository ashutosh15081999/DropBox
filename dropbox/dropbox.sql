-- Create Database 
create database dropbox;

use dropbox;

-- Create table
-- +-----------------------+---------------+------+-----+-------------------+-----------------------------------------------+
-- | Field                 | Type          | Null | Key | Default           | Extra                                         |
-- +-----------------------+---------------+------+-----+-------------------+-----------------------------------------------+
-- | id                    | int           | NO   | PRI | NULL              | auto_increment                                |
-- | fileID                | varchar(64)   | NO   |     | NULL              |                                               |
-- | fileName              | varchar(45)   | NO   |     | NULL              |                                               |
-- | fileSize              | decimal(15,5) | YES  |     | NULL              |                                               |
-- | createdTimeStamp      | timestamp     | NO   |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED                             |
-- | LastModifiedTimeStamp | timestamp     | NO   |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED on update CURRENT_TIMESTAMP |
-- | isDeleted             | varchar(1)    | YES  |     | F                 |                                               |
-- +-----------------------+---------------+------+-----+-------------------+-----------------------------------------------+
create table FileMetaData(
	id int NOT NULL AUTO_INCREMENT,  
	fileID varchar(32) NOT NULL,
	fileName varchar(45) NOT NULL, 
	fileSize decimal(15,5),
	createdTimeStamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	LastModifiedTimeStamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)  
);
