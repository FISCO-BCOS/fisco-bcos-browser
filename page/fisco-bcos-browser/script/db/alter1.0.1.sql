#建交易表的联合索引
ALTER TABLE tb_transaction ADD INDEX index1(blockNumber,transactionIndex);