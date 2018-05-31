package org.bcos.browser.util;

/**
 * 常量定义
 */
public interface Constants {

    /** 获取当前块高 */
    String ETH_BLOCK_NUMBER			= "eth_blockNumber";
    
    /** 根据块高获取block信息 */
    String ETH_GET_BLOCK_BY_NUMBER	= "eth_getBlockByNumber";
    
    /** 获取PBFT VIEW */
    String ETH_PBFT_VIEW			= "eth_pbftView";

    /** 获取正在处理但还未上链的交易 */
    String ETH_PENDING_TRANSACTIONS	= "eth_pendingTransactions";
    
    /** 获取交易回执信息 */
    String ETH_GET_TRANSACTION_RECEIPT	= "eth_getTransactionReceipt";
    
    /** 获取节点自身信息 */
    String ADMIN_NODE_INFO	= "admin_nodeInfo";
    
    /** 获取其它节点信息 */
    String ADMIN_PEERS	= "admin_peers";
    
    /** MONITOR日志格式定义 */
    String MONITOR_LOG_FORMAT		= "[{\"CODE\":\"%s\",\"COST_TIME\":\"{}\",\"RES_CODE\":\"0\"}][{}]";
    
    /** GovernService耗时记录 */
    String CODE_MONI_10000			= String.format(MONITOR_LOG_FORMAT, "10000");
    String MSG_MONI_10000			= "handleBlockChainInfo";
    
    /** handleBlockInfo耗时记录 */
    String CODE_MONI_10001			= String.format(MONITOR_LOG_FORMAT, "10001");
    String MSG_MONI_10001			= "handleBlockInfo";
    
    /** handleTransInfo耗时记录 */
    String CODE_MONI_10002			= String.format(MONITOR_LOG_FORMAT, "10002");
    String MSG_MONI_10002			= "handleTransInfo";
    
    /** handlePendingTransInfo耗时记录 */
    String CODE_MONI_10003			= String.format(MONITOR_LOG_FORMAT, "10003");
    String MSG_MONI_10003			= "handlePendingTransInfo";
    
    /** selectBlockHeigth耗时记录 */
    String CODE_MONI_10004			= String.format(MONITOR_LOG_FORMAT, "10004");
    String MSG_MONI_10004			= "selectBlockHeigth";
    
    /** handleNodeInfo耗时记录 */
    String CODE_MONI_10005			= String.format(MONITOR_LOG_FORMAT, "10005");
    String MSG_MONI_10005			= "handleNodeInfo";
    
}
