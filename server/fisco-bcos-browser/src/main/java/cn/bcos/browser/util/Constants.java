/*
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: Constants.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.util;

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
