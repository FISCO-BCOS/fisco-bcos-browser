package org.bcos.browser.bcos.browser.dao;

import java.sql.Timestamp;
import java.util.List;

import org.bcos.browser.bcos.browser.dto.BlockChainInfoDTO;
import org.bcos.browser.bcos.browser.dto.BlockInfoDTO;
import org.bcos.browser.bcos.browser.dto.NodeInfoDTO;
import org.bcos.browser.bcos.browser.dto.PeerRpcDTO;
import org.bcos.browser.bcos.browser.dto.ReceiptInfoDTO;
import org.bcos.browser.bcos.browser.dto.TransactionInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface GovernServiceDAO {
	
	public int selectBlockChainHeigth();
	
	public int selectBlockHeigth();
	
	public int selectBlockInfoCount();
	
	public int selectTransactionInfoCount();
	
	public int selectPendingTransInfoCount();
	
	public List<Timestamp> selectTimestamp();
	
	public void insertBlockInfo(BlockInfoDTO blockInfo);
	
	public void insertBlockChainInfo(BlockChainInfoDTO blockChainInfo);
	
	public void insertTransactionInfo(TransactionInfoDTO transactionInfo);
	
	public void insertReceiptInfo(ReceiptInfoDTO receiptInfoDTO);
	
	public void insertPendingTransInfo(TransactionInfoDTO transactionInfo);
	
	public void insertTxnByDayInfo();
	
	public void insertNodeInfo(NodeInfoDTO nodeInfoDTO);
	
	public void updateBlockInfo(BlockChainInfoDTO blockChainInfo);
	
	public void deletePendingTransInfo();
	
	public void deleteNodeInfo();
	
	public void deleteRpcNode(NodeInfoDTO nodeInfoDTO);
	
	public List<String> selectPkId();
	
	public void updateActive(NodeInfoDTO nodeInfoDTO);
	
	public void updateAllActive();
	
	public void updateBlocknum(NodeInfoDTO nodeInfoDTO);
	
	public void updateIp(NodeInfoDTO nodeInfoDTO);
	
	public List<PeerRpcDTO> selectPeerRpc();

}
