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
 * @file: GovernServiceDAO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bcos.browser.dto.BlockChainInfoDTO;
import cn.bcos.browser.dto.BlockInfoDTO;
import cn.bcos.browser.dto.NodeInfoDTO;
import cn.bcos.browser.dto.PeerRpcDTO;
import cn.bcos.browser.dto.ReceiptInfoDTO;
import cn.bcos.browser.dto.TransactionInfoDTO;

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
