-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
CREATE TABLE IF NOT EXISTS tb_group (
    group_id int(11) NOT NULL COMMENT '群组ID',
    group_name varchar(128) NOT NULL COMMENT '群组名称',
    group_desc varchar(1024) COMMENT '群组描述',
    gmt_create datetime COMMENT '创建时间',
    gmt_modify datetime COMMENT '修改时间',
    PRIMARY KEY (group_id)
) COMMENT='群组信息表' ENGINE=InnoDB CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_blockchaininfo
-- ----------------------------
CREATE TABLE IF NOT EXISTS tb_blockchaininfo (
    group_id int(11) NOT NULL COMMENT '所属群组ID',
    latest_number int(11) COMMENT '最新块高',
    txn int(11) COMMENT '交易量',
    pending_txn int(11) COMMENT '正在处理但还未上链的交易个数',
    pbft_view int(11) COMMENT 'PBFT VIEW',
    gmt_create datetime COMMENT '创建时间',
    gmt_modify datetime COMMENT '修改时间',
    PRIMARY KEY (group_id)
) COMMENT='区块链全局信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_contract
-- ----------------------------
CREATE TABLE IF NOT EXISTS tb_contract (
    contract_id int(11) NOT NULL AUTO_INCREMENT COMMENT '合约编号',
    contract_name varchar(128) COMMENT '合约名称',
    contract_source text COMMENT '合约源码',
    contract_status tinyint(4) DEFAULT '0' COMMENT '编译（0：未编译，1：编译成功，2：编译失败）',
    contract_abi text COMMENT '编译合约生成的abi文件内容',
    contract_bin text COMMENT '合约binary',
    contract_path varchar(128) COMMENT '合约相对路径',
    contract_address varchar(128) COMMENT '合约地址',
    contract_desc text COMMENT '描述',
    error_info text COMMENT '编译错误信息',
    gmt_create datetime COMMENT '创建时间',
    gmt_modify datetime COMMENT '修改时间',
    PRIMARY KEY (contract_id),
    UNIQUE KEY (contract_name, contract_path)
) COMMENT='合约信息表' ENGINE=InnoDB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_function
-- ----------------------------
CREATE TABLE IF NOT EXISTS tb_function (
    method_id varchar(128) COMMENT '方法id',
    abi_info text COMMENT 'abi信息',
    type varchar(32) COMMENT '方法类型',
    gmt_create datetime COMMENT '创建时间',
    gmt_modify datetime COMMENT '修改时间',
    PRIMARY KEY (method_id)
) COMMENT='方法解析信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS tb_user (
    user_id int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
    user_name varchar(64) binary NOT NULL COMMENT '用户名',
    group_id int(11) DEFAULT NULL COMMENT '所属群组编号',
    address varchar(64) DEFAULT NULL COMMENT '公钥地址',
    description varchar(250) DEFAULT NULL COMMENT '备注',
    create_time datetime DEFAULT NULL COMMENT '创建时间',
    modify_time datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (user_id),
    UNIQUE KEY unique_name (group_id,user_name),
    KEY index_address (address)
) COMMENT='用户信息表' ENGINE=InnoDB AUTO_INCREMENT=200001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_node
-- ----------------------------
CREATE TABLE IF NOT EXISTS tb_node (
    node_id varchar(192) NOT NULL COMMENT '节点ID',
    group_id int(11) NOT NULL COMMENT '所属群组id',
    ip varchar(16) COMMENT '节点ip',
    rpc_port varchar(16) COMMENT '节点rpc端口',
    p2p_port varchar(16) COMMENT '节点p2p端口',
    block_number int(11) DEFAULT 0 COMMENT '节点块高',
    pbft_view int(11) DEFAULT 0 COMMENT 'PBFT view',
    status tinyint(4) DEFAULT '0' COMMENT '节点状态（0：正常，1：异常）',
    type tinyint(4) DEFAULT '0' COMMENT '节点类型（0：手动添加，1：链上同步）',
    gmt_create datetime COMMENT '创建时间',
    gmt_modify datetime COMMENT '修改时间',
    PRIMARY KEY (group_id,node_id)
) COMMENT='节点信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_trans_daily
-- ----------------------------
CREATE TABLE IF NOT EXISTS tb_txn_daily (
    pk_date date NOT NULL COMMENT '时间',
    group_id int(11) NOT NULL COMMENT '所属群组ID',
    txn int(11) COMMENT '交易量',
    block_number int(11) DEFAULT '0' COMMENT '当前统计到的块高',
    gmt_create datetime COMMENT '创建时间',
    gmt_modify datetime COMMENT '修改时间',
    PRIMARY KEY (group_id,pk_date)
) COMMENT='每日交易量记录表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

