ALTER TABLE tb_transaction_1 ADD block_date date NOT NULL COMMENT '出块年月日' after block_time;
ALTER TABLE tb_transaction_1 DROP KEY group_id;
ALTER TABLE tb_transaction_1 DROP KEY block_number;
ALTER TABLE tb_transaction_1 DROP KEY trans_index;
ALTER TABLE tb_transaction_1 ADD UNIQUE KEY idx_bt (block_number,trans_index);
ALTER TABLE tb_transaction_1 ADD KEY idx_bg (block_date,group_id);
ALTER TABLE tb_transaction_1 ADD KEY idx_gi (group_id);


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
) ENGINE=InnoDB AUTO_INCREMENT=300001 DEFAULT CHARSET=utf8 COMMENT='合约信息表';


CREATE TABLE IF NOT EXISTS tb_function (
	method_id varchar(128) COMMENT '方法id',
	abi_info text COMMENT 'abi信息',
	type varchar(32) COMMENT '方法类型',
	gmt_create datetime COMMENT '创建时间',
	gmt_modify datetime COMMENT '修改时间',
	PRIMARY KEY (method_id)
) COMMENT='方法解析信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;
