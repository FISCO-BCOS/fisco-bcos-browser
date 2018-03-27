/* 定义DELIMITER 为// */
DELIMITER //
CREATE PROCEDURE procedure_create_table_tb_single_stat_every_day()
BEGIN
	DECLARE suffix VARCHAR(15);
	DECLARE sqlstr VARCHAR(2560);
	SET @suffix = DATE_FORMAT(CURDATE(),'%Y%m%d');
	SET @sqlstr = CONCAT(
	"CREATE TABLE IF NOT EXISTS tb_single_stat_",
	@suffix,
	"(
	  pk_id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	  object varchar(64) COMMENT '区块链节点',
	  attr varchar(64) COMMENT '属性',
	  attrName varchar(64) COMMENT '属性名',
	  collectTimestamp timestamp(3) COMMENT '时间戳',
	  metricValue double COMMENT '上报的数据内容',
	  hostIp varchar(64) COMMENT '数据源IP',
	  detailInfo longtext COMMENT 'rpc查询结果的所有数据',
	  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
	  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
	  PRIMARY KEY (pk_id)
	) COMMENT='单点统计信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	);
	PREPARE create_stmt FROM @sqlstr;
	EXECUTE create_stmt;
END//
DELIMITER ;

/* 创建交易流程统计信息表的存储过程 */
DELIMITER //
CREATE PROCEDURE procedure_create_table_tb_stat_transaction_every_day()
BEGIN
	DECLARE suffix VARCHAR(15);
	DECLARE sqlstr VARCHAR(2560);
	SET @suffix = DATE_FORMAT(CURDATE(),'%Y%m%d');
	SET @sqlstr = CONCAT(
	"CREATE TABLE IF NOT EXISTS tb_stat_transaction_",
	@suffix,
	"(
      pk_id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	  hash varchar(256) NOT NULL COMMENT 'HASH',  
	  object varchar(64) COMMENT '区块链节点',
	  collectTimestamp timestamp(3) COMMENT '时间戳',
	  startMsg varchar(256) COMMENT '交易开始描述',
	  startTime varchar(64) COMMENT '交易开始时间',
	  onChainMsg varchar(256) COMMENT '交易上链描述',
	  onChainTime varchar(64) COMMENT '交易上链时间',
	  detailInfo longtext COMMENT '上报上来的json信息',
	  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
	  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
	  PRIMARY KEY (pk_id)
	) COMMENT='交易流程统计信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	);
	PREPARE create_stmt FROM @sqlstr;
	EXECUTE create_stmt;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE procedure_create_table_tb_stat_block_every_day()
BEGIN
	DECLARE suffix VARCHAR(15);
	DECLARE sqlstr VARCHAR(2560);
	SET @suffix = DATE_FORMAT(CURDATE(),'%Y%m%d');
	SET @sqlstr = CONCAT(
	"CREATE TABLE IF NOT EXISTS tb_stat_block_",
	@suffix,
	"(
      pk_id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	  height int(11) NOT NULL COMMENT '块高',  
	  object varchar(64) COMMENT '区块链节点',
      collectTimestamp timestamp(3) COMMENT '时间戳',
	  hash varchar(256) COMMENT '区块HASH',
	  start varchar(64) COMMENT '开始时间',
	  sealed varchar(64) COMMENT '打包时间',
	  execed varchar(64) COMMENT '执行时间',
	  signed varchar(64) COMMENT '签名时间',
	  commited varchar(64) COMMENT '确认时间',
	  onChain varchar(64) COMMENT '上链时间',
	  viewchange_start varchar(64) COMMENT 'view调整开始时间',
	  viewchanged varchar(64) COMMENT 'view调整结束时间',
	  detailInfo longtext COMMENT '上报上来的json信息',
	  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
	  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
	  PRIMARY KEY (pk_id)
	) COMMENT='出块流程统计信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	);
	PREPARE create_stmt FROM @sqlstr;
	EXECUTE create_stmt;
END//
DELIMITER ;

/* 创建当前日期的表 */
CALL procedure_create_table_tb_single_stat_every_day();
CALL procedure_create_table_tb_stat_transaction_every_day();
CALL procedure_create_table_tb_stat_block_every_day();


/* 定义事件根据时间每天建一张表 */
CREATE EVENT event_create_table_tb_single_stat_every_day
ON SCHEDULE EVERY 1 day
STARTS DATE_SUB(CURDATE(),INTERVAL 1 DAY)
ON COMPLETION PRESERVE
ENABLE
DO
CALL procedure_create_table_tb_single_stat_every_day();

CREATE EVENT event_create_table_tb_stat_transaction_every_day
ON SCHEDULE EVERY 1 day
STARTS DATE_SUB(CURDATE(),INTERVAL 1 DAY)
ON COMPLETION PRESERVE
ENABLE
DO
CALL procedure_create_table_tb_stat_transaction_every_day();

CREATE EVENT event_create_table_tb_stat_block_every_day
ON SCHEDULE EVERY 1 day
STARTS DATE_SUB(CURDATE(),INTERVAL 1 DAY)
ON COMPLETION PRESERVE
ENABLE
DO
CALL procedure_create_table_tb_stat_block_every_day();

/* 创建插入数据的存储过程 */
DELIMITER //
CREATE PROCEDURE procedure_insert_table_tb_single_stat_by_day(
	IN object varchar(64), 
	IN attr varchar(64), 
	IN attrName varchar(64), 
	IN collectTimestamp timestamp(3), 
	IN metricValue double, 
	IN hostIp varchar(64), 
	IN detailInfo longtext
	)
BEGIN
	DECLARE suffix VARCHAR(15);
	DECLARE sqlstr VARCHAR(2560);
	SET @object = object; 
	SET @attr = attr;
	SET @attrName = attrName;
	SET @collectTimestamp = collectTimestamp;
	SET @metricValue = metricValue;
	SET @hostIp = hostIp;
	SET @detailInfo = detailInfo;
	SET @suffix = DATE_FORMAT(CURDATE(),'%Y%m%d');
	SET @sqlstr = CONCAT(
	"INSERT INTO tb_single_stat_",
	@suffix,
	"(
	  object, attr, attrName, collectTimestamp, metricValue, hostIp, detailInfo, gmt_create, gmt_modify
	) values(
	  @object, @attr, @attrName, @collectTimestamp, @metricValue, @hostIp, @detailInfo, NOW(), NOW()
	)"
	);
	PREPARE insert_stmt FROM @sqlstr;
	EXECUTE insert_stmt;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE procedure_insert_table_tb_stat_transaction_by_day(
	IN hash varchar(256), 
	IN object varchar(64), 
	IN collectTimestamp timestamp(3),
	IN startMsg varchar(256), 
	IN startTime varchar(64), 
	IN onChainMsg varchar(256), 
	IN onChainTime varchar(64), 
	IN detailInfo longtext
	)
BEGIN
	DECLARE suffix VARCHAR(15);
	DECLARE sqlstr VARCHAR(2560);
	SET @hash = hash; 
	SET @object = object; 
	SET @collectTimestamp = collectTimestamp;
	SET @startMsg = startMsg;
	SET @startTime = startTime;
	SET @onChainMsg = onChainMsg;
	SET @onChainTime = onChainTime;
	SET @detailInfo = detailInfo;
	SET @suffix = DATE_FORMAT(CURDATE(),'%Y%m%d');
	SET @sqlstr = CONCAT(
	"INSERT INTO tb_stat_transaction_",
	@suffix,
	"(
	  hash, object, collectTimestamp, startMsg, startTime, onChainMsg, onChainTime, detailInfo, gmt_create, gmt_modify
	) values(
	  @hash, @object, @collectTimestamp, @startMsg, @startTime, @onChainMsg, @onChainTime, @detailInfo, NOW(), NOW()
	)"
	);
	PREPARE insert_stmt FROM @sqlstr;
	EXECUTE insert_stmt;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE procedure_insert_table_tb_stat_block_by_day(
	IN height int(11), 
	IN object varchar(64), 
	IN collectTimestamp timestamp(3),
	IN hash varchar(256), 
	IN start varchar(64), 
	IN sealed varchar(64),  
	IN execed varchar(64), 
	IN signed varchar(64), 
	IN commited varchar(64), 
	IN onChain varchar(64), 
	IN viewchange_start varchar(64),  
	IN viewchanged varchar(64), 
	IN detailInfo longtext
	)
BEGIN
	DECLARE suffix VARCHAR(15);
	DECLARE sqlstr VARCHAR(2560);
	SET @height = height; 
	SET @object = object; 
	SET @collectTimestamp = collectTimestamp;
	SET @hash = hash;
	SET @start = start;
	SET @sealed = sealed;
	SET @execed = execed;
	SET @signed = signed; 
	SET @commited = commited;
	SET @onChain = onChain;
	SET @viewchange_start = viewchange_start;
	SET @viewchanged = viewchanged;
	SET @detailInfo = detailInfo;
	SET @suffix = DATE_FORMAT(CURDATE(),'%Y%m%d');
	SET @sqlstr = CONCAT(
	"INSERT INTO tb_stat_block_",
	@suffix,
	"(
	  height, object, collectTimestamp, hash, start, sealed, execed, signed, commited, onChain, viewchange_start, viewchanged, detailInfo, gmt_create, gmt_modify
	) values(
	  @height, @object, @collectTimestamp, @hash, @start, @sealed, @execed, @signed, @commited, @onChain, @viewchange_start, @viewchanged, @detailInfo, NOW(), NOW()
	)"
	);
	PREPARE insert_stmt FROM @sqlstr;
	EXECUTE insert_stmt;
END//
DELIMITER ;
