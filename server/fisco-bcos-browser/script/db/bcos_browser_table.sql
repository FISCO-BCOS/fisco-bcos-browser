CREATE TABLE tb_blockChainInfo (
  pk_id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  lastBlock int(11) COMMENT '最新块高',
  txn bigint(20) COMMENT '交易量',
  pendingTxn bigint(20) COMMENT '正在处理但还未上链的交易的个数',
  pbftView int(11) COMMENT 'PBFT VIEW',
  avgTime decimal(5,2) COMMENT '相邻两个块的timestamp之差的平均值',
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (pk_id)
) COMMENT='区块链全局信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_txnByDay (
  pk_date date NOT NULL COMMENT '交易时间',
  txn bigint(20) COMMENT '交易量',
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (pk_date)
) COMMENT='每日交易量记录表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_block (
  pk_hash varchar(128) NOT NULL COMMENT '块hash值',
  number int(11) NOT NULL COMMENT '高度',
  parentHash varchar(128) COMMENT '上级hash',
  miner varchar(256) NOT NULL COMMENT '矿工',
  genIndex int(11) NOT NULL,
  size int(11),
  gasLimit bigint(20),
  gasUsed bigint(20),
  avgGasPrice decimal(30,8) COMMENT '块内交易的平均gasPrice',
  timestamp timestamp(3) NOT NULL,
  txn bigint(20) COMMENT '块包含的交易数',
  extraData text,
  detailInfo longtext COMMENT 'rpc查询结果的所有数据',
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (pk_hash)
) COMMENT='区块信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_transaction (
  pk_hash varchar(128) NOT NULL COMMENT '交易hash值',
  blockHash varchar(128) COMMENT '块hash值',
  blockNumber int(11) COMMENT '块高',
  blockTimestamp timestamp(3),
  blockGasLimit bigint(20),
  transactionIndex bigint(20),
  transactionFrom varchar(64),
  transactionTo varchar(64),
  gas bigint(20),
  gasPrice decimal(30,8),
  cumulativeGas bigint(20),
  randomId varchar(128),
  contractName longtext COMMENT '合约名',
  version longtext COMMENT '合约版本',
  method longtext COMMENT '被调用的合约中的函数名',
  params longtext COMMENT '被调用的函数的参数',
  inputText longtext,
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (pk_hash)
) COMMENT='交易信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_pendingTransaction (
  pk_hash varchar(128) NOT NULL COMMENT '交易hash值',
  blockHash varchar(128) COMMENT '块hash值',
  blockNumber int(11) COMMENT '块高',
  transactionIndex bigint(20),
  transactionFrom varchar(64),
  transactionTo varchar(64),
  gas bigint(20),
  gasPrice decimal(30,8),
  cumulativeGas bigint(20),
  randomId varchar(128),
  inputText longtext,
  type varchar(64) COMMENT '队列类型',
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (pk_hash)
) COMMENT='未上链交易信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_nodesInfo (
  pk_id varchar(128) NOT NULL DEFAULT '' COMMENT '节点id',
  addr varchar(32) DEFAULT NULL COMMENT '节点ip和p2p_port',
  blockNumber int(11) DEFAULT NULL COMMENT '节点块高',
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
  active varchar(10) NOT NULL COMMENT '节点存活标识',
  PRIMARY KEY (pk_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点信息表';

CREATE TABLE tb_transactionReceipt (
  pk_hash varchar(128) NOT NULL COMMENT '交易hash值',
  blockHash varchar(128) COMMENT '块hash值',
  blockNumber int(11) COMMENT '块高',
  contractAddress varchar(128) COMMENT '合约地址',
  transactionIndex bigint(20) COMMENT '交易顺序',
  gasUsed bigint(20) COMMENT '已用gas',
  cumulativeGasUsed bigint(20) COMMENT '累计已用gas',
  logs longtext COMMENT 'logs信息',
  detailInfo longtext COMMENT 'rpc查询结果的所有数据',
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_modify datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (pk_hash)
) COMMENT='交易回执信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_nodeConnection (
  pk_id int(11) NOT NULL AUTO_INCREMENT,
  ip varchar(16) NOT NULL COMMENT '节点ip',
  rpcPort  int(11) NOT NULL COMMENT '节点rpc端口',
  PRIMARY KEY (pk_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点rpc链接表';

ALTER TABLE tb_block ADD INDEX index_number (number);

ALTER TABLE tb_transaction ADD INDEX index_number(blockNumber,transactionIndex);
