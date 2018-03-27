package org.bcos.browser.bcos.browser.util;

/**
 * Constants
 */
public interface Constants {

    /** gets thecurrent block height */
    String ETH_BLOCK_NUMBER			= "eth_blockNumber";
    
    /** obtain block information according to block height */
    String ETH_GET_BLOCK_BY_NUMBER	= "eth_getBlockByNumber";
    
    /** get PBFT VIEW */
    String ETH_PBFT_VIEW			= "eth_pbftView";

    /** get pending transactions */
    String ETH_PENDING_TRANSACTIONS	= "eth_pendingTransactions";
    
    /** get transaction receipt */
    String ETH_GET_TRANSACTION_RECEIPT	= "eth_getTransactionReceipt";
    
    /** get nodeInfo */
    String ADMIN_NODE_INFO	= "admin_nodeInfo";
    
    /** get other nodeInfo */
    String ADMIN_PEERS	= "admin_peers";
    
    /** MONITOR_log_format */
    String MONITOR_LOG_FORMAT		= "[{\"CODE\":\"%s\",\"COST_TIME\":\"{}\",\"RES_CODE\":\"0\"}][{}]";
    
    /** GovernService take record */
    String CODE_MONI_10000			= String.format(MONITOR_LOG_FORMAT, "10000");
    String MSG_MONI_10000			= "handleBlockChainInfo";
    
    /** handleBlockInfo take record */
    String CODE_MONI_10001			= String.format(MONITOR_LOG_FORMAT, "10001");
    String MSG_MONI_10001			= "handleBlockInfo";
    
    /** handleTransInfo take record*/
    String CODE_MONI_10002			= String.format(MONITOR_LOG_FORMAT, "10002");
    String MSG_MONI_10002			= "handleTransInfo";
    
    /** handlePendingTransInfo take record */
    String CODE_MONI_10003			= String.format(MONITOR_LOG_FORMAT, "10003");
    String MSG_MONI_10003			= "handlePendingTransInfo";
    
    /** selectBlockHeigth take record */
    String CODE_MONI_10004			= String.format(MONITOR_LOG_FORMAT, "10004");
    String MSG_MONI_10004			= "selectBlockHeigth";
    
    /** handleNodeInfo take record */
    String CODE_MONI_10005			= String.format(MONITOR_LOG_FORMAT, "10005");
    String MSG_MONI_10005			= "handleNodeInfo";
    
}
