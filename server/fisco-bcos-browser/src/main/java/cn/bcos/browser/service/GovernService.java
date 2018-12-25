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
 * @file: GovernService.java
 * @author: v_sfqiliu
 * @date: 2018
 */
 
package cn.bcos.browser.service;

import static cn.bcos.browser.util.Constants.*;
import static cn.bcos.browser.util.LogUtils.getMonitorLogger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bcos.web3j.abi.FunctionReturnDecoder;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import cn.bcos.browser.contract.BuildSolidityParams;
import cn.bcos.browser.contract.Contract;
import cn.bcos.browser.contract.EventResult;
import cn.bcos.browser.dao.GovernServiceDAO;
import cn.bcos.browser.dto.AddWarrantEventDTO;
import cn.bcos.browser.dto.BlockChainInfoDTO;
import cn.bcos.browser.dto.BlockInfoDTO;
import cn.bcos.browser.dto.CnsContractDTO;
import cn.bcos.browser.dto.NodeInfoDTO;
import cn.bcos.browser.dto.PeerRpcDTO;
import cn.bcos.browser.dto.ReceiptInfoDTO;
import cn.bcos.browser.dto.TransactionInfoDTO;
import cn.bcos.browser.dto.MarketAuctionSuccessEventDTO;

import static org.bcos.web3j.abi.Utils.convert;
/**
 * blockChain management service
 */
@Component
public class GovernService {
	private Logger logger = LoggerFactory.getLogger(GovernService.class);

	public void start(){
	    System.out.println("Start");
		handleBlockChainInfo();
		System.out.println("end");
	}

	/**
	 * Handle global information
	 */
	public void handleBlockChainInfo(){
		long startTime = System.currentTimeMillis();
		String lastBlock = (String) getInfoByMethod(ETH_BLOCK_NUMBER, null);
		if(lastBlock==null){
			return;
		}
		int blockHeight = Integer.parseInt(lastBlock.substring(2), 16);
		logger.debug("###handleBlockChainInfo the latest blockHeight：{}###", blockHeight);
		System.out.println("###handleBlockChainInfo the latest blockHeight："+blockHeight);
		if (blockHeight == 0) {
			return;
		} 
		// get db blockHeight
		int blockChainHeigth = governServiceDAO.selectBlockChainHeigth();
		logger.debug("###handleBlockChainInfo db blockHeight：{}###", blockChainHeigth);
		// get pbftView
		String pbftView = (String) getInfoByMethod(ETH_PBFT_VIEW, null);
		if(pbftView==null){
			return;
		}
		int txn = governServiceDAO.selectTransactionInfoCount();
		int pendingTxn = governServiceDAO.selectPendingTransInfoCount();
		blockChainInfoDTO.setLastBlock(blockHeight);
		blockChainInfoDTO.setTxn(txn);
		blockChainInfoDTO.setPendingTxn(pendingTxn);
		blockChainInfoDTO.setAvgTime(new BigDecimal(100));
		blockChainInfoDTO.setPbftView(Long.parseLong(pbftView.substring(2), 16));
		if (blockChainHeigth == 0) {
			governServiceDAO.insertBlockChainInfo(blockChainInfoDTO);
		} else {
			governServiceDAO.updateBlockInfo(blockChainInfoDTO);
		}
		long endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10000, endtTime - startTime, MSG_MONI_10000);
	}
	
	/**
	 * handle blockInfo
	 */
	public void handleBlockInfo() {
		long startTime = System.currentTimeMillis();
		String lastBlock = (String) getInfoByMethod(ETH_BLOCK_NUMBER, null);
		if(lastBlock==null){
			return;
		}
		int blockHeight = Integer.parseInt(lastBlock.substring(2), 16);
		logger.debug("###handleBlockInfo the latest blockHeight：{}###", blockHeight);
		long startTime1 = System.currentTimeMillis();
		int dbBlockHeight = governServiceDAO.selectBlockHeigth();
		long endtTime1 = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10004, endtTime1 - startTime1, MSG_MONI_10004);
		logger.debug("###handleBlockInfo db blockHeight：{}###", dbBlockHeight);
		
		long endtTime = 0;
		if (blockHeight == 0 || blockHeight == dbBlockHeight) {
			endtTime = System.currentTimeMillis();
			getMonitorLogger().info(CODE_MONI_10001, endtTime - startTime, MSG_MONI_10001);
			return;
		} else {
			governServiceDAO.insertTxnByDayInfo();
			for (int i = dbBlockHeight + 1; i <= blockHeight; i++) {
				if (i == 1) {
					handleBlockInfo(0);
					handleBlockInfo(1);
				} else {
					handleBlockInfo(i);
				}
			}
		}
		endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10001, endtTime - startTime, MSG_MONI_10001);
		
	}

	/**
	 * handle block info
	 * 
	 * @param blockHeight
	 */
	public void handleBlockInfo(int blockHeight){
		Object[] params = new Object[] { String.valueOf(blockHeight), true };
		Object blockInfo = getInfoByMethod(ETH_GET_BLOCK_BY_NUMBER, params);
		if(blockInfo==null){
			return;
		}
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(blockInfo));
		logger.debug("###blockInfo：{}###", json);

		Map<String, Object> map = handleTransInfo(json);
		BigDecimal gasPriceTotal = new BigDecimal(map.get("gasPriceTotal").toString());
		BigDecimal transCount = new BigDecimal(map.get("transCount").toString());

		blockInfoDTO = new BlockInfoDTO();
		blockInfoDTO.setPk_hash(json.getString("hash"));
		blockInfoDTO.setNumber(Integer.parseInt(json.getString("number").substring(2), 16));
		blockInfoDTO.setParentHash(json.getString("parentHash"));
		//miner--minerNodeId
		blockInfoDTO.setMiner(null!=json.getString("minerNodeId") ?json.getString("minerNodeId"):"0x0");
		blockInfoDTO.setGenIndex(Integer.parseInt(json.getString("genIndex").substring(2), 16));
		blockInfoDTO.setSize(Integer.parseInt(json.getString("size").substring(2), 16));
		blockInfoDTO.setGasLimit(Long.parseLong(json.getString("gasLimit").substring(2), 16));
		blockInfoDTO.setGasUsed(Long.parseLong(json.getString("gasUsed").substring(2), 16));
		if (new BigDecimal("0").equals(gasPriceTotal)) {
			blockInfoDTO.setAvgGasPrice(new BigDecimal("0"));
		} else {
			blockInfoDTO.setAvgGasPrice(gasPriceTotal.divide(transCount, 8, BigDecimal.ROUND_HALF_UP));
		}
		if ("0".equals(json.getString("timestamp").substring(2))) {
			blockInfoDTO.setTimestamp(null);
		} else {
			blockInfoDTO.setTimestamp(new Timestamp(Long.parseLong(json.getString("timestamp").substring(2), 16)));
		}
		blockInfoDTO.setTxn(Long.parseLong(map.get("transCount").toString()));
		blockInfoDTO.setExtraData(json.getString("extraData"));
		blockInfoDTO.setDetailInfo(json.toString());
		
		governServiceDAO.insertBlockInfo(blockInfoDTO);
		handleCnsContract(String.valueOf(blockHeight));
	}

	/**
	 * handle transaction info
	 * 
	 * @param json
	 * @return Map<String, Object>
	 */
	public Map<String, Object> handleTransInfo(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		long gasPriceTotal = 0;
		JSONArray jsonArr = json.getJSONArray("transactions");
		logger.debug("###transactions：{}###", jsonArr);
		long jsonSize = jsonArr.size();
		System.out.println("Tx number: "+jsonArr.size());
		map.put("transCount", jsonSize);
		map.put("gasPriceTotal", gasPriceTotal);
		for (int j = 0; j < jsonSize; j++) {
			JSONObject jsonTrans = jsonArr.getJSONObject(j);
			gasPriceTotal = gasPriceTotal + Long.parseLong(jsonTrans.getString("gasPrice").substring(2), 16);
			transactionInfoDTO = new TransactionInfoDTO();
			transactionInfoDTO.setPk_hash(jsonTrans.getString("hash"));
			transactionInfoDTO.setBlockHash(jsonTrans.getString("blockHash"));
			transactionInfoDTO.setBlockNumber(Integer.parseInt(jsonTrans.getString("blockNumber").substring(2), 16));
			if ("0".equals(json.getString("timestamp").substring(2))) {
				transactionInfoDTO.setBlockTimestamp(null);
			} else {
			    Timestamp timestamp = new Timestamp(Long.parseLong(json.getString("timestamp").substring(2), 16));
				transactionInfoDTO.setBlockTimestamp(timestamp);
		        Contract.setTimestamp(timestamp);
			}
			transactionInfoDTO.setBlockGasLimit(Long.parseLong(json.getString("gasLimit").substring(2), 16));
			transactionInfoDTO.setTransactionIndex(Long.parseLong(jsonTrans.getString("transactionIndex").substring(2), 16));
			transactionInfoDTO.setTransactionFrom(jsonTrans.getString("from"));
			Contract.setTxFrom(jsonTrans.getString("from"));
			transactionInfoDTO.setTransactionTo(jsonTrans.getString("to"));
			transactionInfoDTO.setGas(Long.parseLong(jsonTrans.getString("gas").substring(2), 16));
			transactionInfoDTO.setGasPrice(BigDecimal.valueOf(Long.parseLong(jsonTrans.getString("gasPrice").substring(2), 16)));
			if (j == 0) {
				transactionInfoDTO.setCumulativeGas(Long.parseLong(jsonTrans.getString("gas").substring(2), 16));
			} else {
				long cumulativeGas = 0;
				for (int k = 0; k <= j; k++) {
					JSONObject jsonObj = jsonArr.getJSONObject(k);
					cumulativeGas = cumulativeGas + Long.parseLong(jsonObj.getString("gas").substring(2), 16);
				}
				transactionInfoDTO.setCumulativeGas(cumulativeGas);
			}
			transactionInfoDTO.setRandomId(jsonTrans.getString("randomId"));
			transactionInfoDTO.setInputText(jsonTrans.getString("input"));
			
			transactionInfoDTO.setContractName(null!=jsonTrans.getJSONObject("operation")?jsonTrans.getJSONObject("operation").getString("contractName"):"");
			transactionInfoDTO.setVersion(null!=jsonTrans.getJSONObject("operation")?jsonTrans.getJSONObject("operation").getString("version"):"");
			transactionInfoDTO.setMethod(null!=jsonTrans.getJSONObject("operation")?jsonTrans.getJSONObject("operation").getString("method"):"");
			transactionInfoDTO.setParams(null!=jsonTrans.getJSONObject("operation")?jsonTrans.getJSONObject("operation").getString("params"):"");
			String pk_hash=governServiceDAO.selectPkHash(jsonTrans.getString("hash") );
			if("".equals(pk_hash)||null==pk_hash){
				governServiceDAO.insertTransactionInfo(transactionInfoDTO);
			}else{
				return map;
			}
			handleTransReceiptInfo(jsonTrans.getString("hash"));
		}
		return map;
	}
	
	/**
	 * handle transaction receipt info
	 */
	public void handleTransReceiptInfo(String hash){
		Object[] params = new Object[] { hash };
		Object receiptInfo = getInfoByMethod(ETH_GET_TRANSACTION_RECEIPT, params);
		if(receiptInfo==null){
			return;
		}
		JSONObject receiptJson = JSONObject.parseObject(JSON.toJSONString(receiptInfo));
		receiptInfoDTO.setPk_hash(receiptJson.getString("transactionHash"));
		receiptInfoDTO.setBlockHash(receiptJson.getString("blockHash"));
		receiptInfoDTO.setBlockNumber(Integer.parseInt(receiptJson.getString("blockNumber")));
		receiptInfoDTO.setContractAddress(receiptJson.getString("contractAddress"));
		receiptInfoDTO.setTransactionIndex(Long.parseLong(receiptJson.getString("transactionIndex")));
		receiptInfoDTO.setGasUsed(Long.parseLong(receiptJson.getString("gasUsed").substring(2), 16));
		receiptInfoDTO.setCumulativeGasUsed(Long.parseLong(receiptJson.getString("cumulativeGasUsed").substring(2), 16));
		receiptInfoDTO.setLogs(receiptJson.getString("logs"));
		receiptInfoDTO.setDetailInfo(receiptJson.toString());
		governServiceDAO.insertReceiptInfo(receiptInfoDTO);
		
		TransactionReceipt receipt = JSONObject.parseObject(JSON.toJSONString(receiptJson),TransactionReceipt.class);
		handleMarketAuctionSuccessEvent(receipt, "Market");
		handleAddWarrantEvent(receipt, "WarrantToken");
	}
	
	public void handleMarketAuctionSuccessEvent(TransactionReceipt receipt, String MarketContractName) {
	    String blockNumber = receipt.getBlockNumberRaw();
	    //获取的合约地址，后续将匹配该合约的事件
	    String addr = Contract.cnsCall("ContractAbiMgr", "getAddr", new String[] {MarketContractName}, blockNumber).get(0);
	    //获取事件的ABI，后续将匹配该事件的ABI
	    AbiDefinition ABI = BuildSolidityParams.toABI(Contract.MarketAuctionSuccessEvent);
        List<EventResult> eventResults = Contract.parseTransactionReceipt(receipt, ABI, addr);
        if(eventResults != null){
            MarketAuctionSuccessEventDTO marketAuctionSuccessEventDTO = new MarketAuctionSuccessEventDTO();
            for(EventResult event:eventResults) {
                marketAuctionSuccessEventDTO.setBlockNumber(receipt.getBlockNumber().intValue());
                marketAuctionSuccessEventDTO.setBlockHash(receipt.getBlockHash());
                marketAuctionSuccessEventDTO.setContractAddress(addr);
                marketAuctionSuccessEventDTO.setContractName(MarketContractName);
                marketAuctionSuccessEventDTO.setTransactionIndex(receipt.getTransactionIndex().longValue());
                marketAuctionSuccessEventDTO.setTransactionHash(receipt.getBlockHash());
                marketAuctionSuccessEventDTO.setEventIndex(event.getIndex());
                marketAuctionSuccessEventDTO.setWarrantID(event.getValues().get(0).getValue().toString()); //Token id
                String fromAddress = String.valueOf(event.getValues().get(1));//from
                marketAuctionSuccessEventDTO.setTransferFromAddress(fromAddress); 
                String toAddress = String.valueOf(event.getValues().get(2)); //to
                marketAuctionSuccessEventDTO.setTransferToAddress(toAddress); 
                marketAuctionSuccessEventDTO.setPrice(event.getValues().get(3).getValue().toString());   //price
                
                List<String> warrantInfo = Contract.cnsCall("WarrantToken", "getWarrant", new String[] {event.getValues().get(0).getValue().toString()}, blockNumber);
                marketAuctionSuccessEventDTO.setWarrantDetail(JSON.toJSONString(warrantInfo));
                
                List<String> fromUserInfo = Contract.cnsCall("IdentityMgr", "getIdentityInfo", new String[] {fromAddress}, blockNumber);
                marketAuctionSuccessEventDTO.setTransferFromName(fromUserInfo.get(0)); //name
                marketAuctionSuccessEventDTO.setTransferFromID(fromUserInfo.get(1));   //id
                
                List<String> toUserInfo = Contract.cnsCall("IdentityMgr", "getIdentityInfo", new String[] {toAddress}, blockNumber);
                marketAuctionSuccessEventDTO.setTransferToName(toUserInfo.get(0)); //name
                marketAuctionSuccessEventDTO.setTransferToID(toUserInfo.get(1));  //id
                marketAuctionSuccessEventDTO.setBlockTimestamp(Contract.getTimestamp());
                /* TODO
                warrantTransferEventDTO.setTransferFromGroup("");
                warrantTransferEventDTO.setTransferToGroup("");
                */
                //检查数据有无重复
                String blockHash = governServiceDAO.selectAuctionSuccessEvent(
                                                        receipt.getBlockNumber().intValue(), 
                                                        receipt.getTransactionIndex(),
                                                        event.getIndex());
                if(blockHash == null) 
                {   //插入数据库
                    System.out.println("insert Warrant transfer");
                    governServiceDAO.insertMarketAuctionSuccessEvent(marketAuctionSuccessEventDTO);
                }else{
                    //有重复数据直接返回
                    System.out.println("insert Warrant failed for repeat data");
                    continue;
                }
            }
        }
	}
	
	public void handleAddWarrantEvent(TransactionReceipt receipt, String WarrantContractName) {
	    String blockNumber = receipt.getBlockNumberRaw();
	    //获取的合约地址，后续将匹配该合约的事件
        String addr = Contract.cnsCall("ContractAbiMgr", "getAddr", new String[] {WarrantContractName}, blockNumber).get(0);
        //获取事件的ABI，后续将匹配该事件的ABI
        AbiDefinition ABI = BuildSolidityParams.toABI(Contract.WarrantTransferEvent);
        List<EventResult> eventResults = Contract.parseTransactionReceipt(receipt, ABI, addr);
        if(eventResults == null) {
            System.out.println("eventResults is null");
            return;
        }
        for(EventResult event:eventResults)
        {
            String fromAddress = String.valueOf(event.getValues().get(0));
            System.out.println("fromAddress: "+fromAddress);
            if(fromAddress.equals("0x0000000000000000000000000000000000000000")) {
                String toAddress = String.valueOf(event.getValues().get(1));
                String tokenID = String.valueOf(event.getValues().get(2));
                List<String> warrantInfo = Contract.cnsCall("WarrantToken", "getWarrant", new String[] {tokenID}, blockNumber);
                String adminAddress = Contract.getTxFrom();
                List<String> adminInfo = Contract.cnsCall("IdentityMgr", "getIdentityInfo", new String[] {adminAddress}, blockNumber);
                List<String> toUserInfo = Contract.cnsCall("IdentityMgr", "getIdentityInfo", new String[] {toAddress}, blockNumber);
                AddWarrantEventDTO addWarrantEventDTO = new AddWarrantEventDTO();
                addWarrantEventDTO.setBlockNumber(receipt.getBlockNumber().intValue());
                addWarrantEventDTO.setBlockHash(receipt.getBlockHash());
                addWarrantEventDTO.setTransactionIndex(receipt.getTransactionIndex().longValue());
                addWarrantEventDTO.setTransactionHash(receipt.getBlockHash());
                addWarrantEventDTO.setWarrantTokenAddress(addr);
                addWarrantEventDTO.setFromAddress(adminAddress);
                addWarrantEventDTO.setFromName(adminInfo.get(0)); //name
                addWarrantEventDTO.setFromID(adminInfo.get(1));   //id
                addWarrantEventDTO.setToAddress(toAddress);
                addWarrantEventDTO.setToName(toUserInfo.get(0)); //name
                addWarrantEventDTO.setToID(toUserInfo.get(1));   //id
                addWarrantEventDTO.setWarrantID(tokenID);
                addWarrantEventDTO.setWarrantDetail(JSON.toJSONString(warrantInfo));
                addWarrantEventDTO.setBlockTimestamp(Contract.getTimestamp());
                //TODO 判重
                governServiceDAO.insertAddWarrantEvent(addWarrantEventDTO);
            }
        }
        
	}
	
	public void handleCnsContract(String blockNumber) {
	    List<String> ret = Contract.cnsCall("ContractAbiMgr", "getAbiCount", new String[] {}, "latest");
	    int abiCount = Integer.valueOf(ret.get(0));
	    for(int i = 0; i < abiCount; ++i) {
	        List<String> cnsInfo = Contract.cnsCall("ContractAbiMgr", "getAllByIndex", new String[] {String.valueOf(i)}, "latest");
	        CnsContractDTO cnsContractDTO = new CnsContractDTO();
	        cnsContractDTO.setAbi(cnsInfo.get(0));
	        cnsContractDTO.setContractAddress(cnsInfo.get(1));
	        cnsContractDTO.setContractName(cnsInfo.get(2));
	        cnsContractDTO.setVersion(cnsInfo.get(3));
	        cnsContractDTO.setStartBlock(cnsInfo.get(4));
	        cnsContractDTO.setEndBlock("latest");
	        String contractName = governServiceDAO.selectCNS(cnsInfo.get(1));
	        if(contractName == null) {
	            System.out.println("insert CNS transfer");
	            governServiceDAO.insertCNS(cnsContractDTO);
	        } else {
	            System.out.println("insert CNS transfer error");
	        }
	    }
	}
	
	
	/**
	 * handle pending transaction info
	 */
	public void handlePendingTransInfo(){
		long startTime = System.currentTimeMillis();
		governServiceDAO.deletePendingTransInfo();
		Object pendingTransactions = getInfoByMethod(ETH_PENDING_TRANSACTIONS, null);
		if(pendingTransactions==null){
			return;
		}
		logger.debug("###pendingTransactions：{}###", pendingTransactions);
		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(pendingTransactions));
		handlePendingTransArr(jsonObject, "current");
		handlePendingTransArr(jsonObject, "pending");
		long endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10003, endtTime - startTime, MSG_MONI_10003);
	}
	
	/**
	 * add handle pending transaction
	 * @param jsonObject
	 * @param type
	 */
	public void handlePendingTransArr(JSONObject jsonObject, String type) {
		JSONArray jsonArr = jsonObject.getJSONArray(type);
		int jsonSize = jsonArr.size();
		for (int i = 0; i < jsonSize; i++) {
			JSONObject jsonCurrent = jsonArr.getJSONObject(i);
			transactionInfoDTO = new TransactionInfoDTO();
			transactionInfoDTO.setPk_hash(jsonCurrent.getString("hash"));
			transactionInfoDTO.setBlockHash(jsonCurrent.getString("blockHash"));
			transactionInfoDTO.setBlockNumber(Integer.parseInt(jsonCurrent.getString("blockNumber").substring(2), 16));
			transactionInfoDTO.setTransactionIndex(Long.parseLong(jsonCurrent.getString("transactionIndex").substring(2), 16));
			transactionInfoDTO.setTransactionFrom(jsonCurrent.getString("from"));
			transactionInfoDTO.setTransactionTo(jsonCurrent.getString("to"));
			transactionInfoDTO.setGas(Long.parseLong(jsonCurrent.getString("gas").substring(2), 16));
			transactionInfoDTO.setGasPrice(BigDecimal.valueOf(Long.parseLong(jsonCurrent.getString("gasPrice").substring(2), 16)));
			if (i == 0) {
				transactionInfoDTO.setCumulativeGas(Long.parseLong(jsonCurrent.getString("gas").substring(2), 16));
			} else {
				long cumulativeGas = 0;
				for (int k = 0; k <= i; k++) {
					JSONObject jsonObj = jsonArr.getJSONObject(k);
					cumulativeGas = cumulativeGas + Long.parseLong(jsonObj.getString("gas").substring(2), 16);
				}
				transactionInfoDTO.setCumulativeGas(cumulativeGas);
			}
			transactionInfoDTO.setRandomId(jsonCurrent.getString("randomId"));
			transactionInfoDTO.setInputText(jsonCurrent.getString("input"));
			transactionInfoDTO.setType(type);
			governServiceDAO.insertPendingTransInfo(transactionInfoDTO);
		}
	}
	
	/**
	 * handle node info
	 */
	public void handleNodeInfo() {
		long startTime = System.currentTimeMillis();
		governServiceDAO.updateAllActive();
		List<PeerRpcDTO> rpcUrl=governServiceDAO.selectPeerRpc();
		for(int j=0;j<rpcUrl.size();j++){
			try{
				JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://"+rpcUrl.get(j).getIp()+":"+rpcUrl.get(j).getRpcPort()));
				String lastBlock = (String) client.invoke(ETH_BLOCK_NUMBER, null, Object.class);
				int blockHeight = Integer.parseInt(lastBlock.substring(2), 16);
				logger.debug("###handleNodeInfo latest blockHeight：{}###", blockHeight);
				Object currentNodeInfo = client.invoke(ADMIN_NODE_INFO, null, Object.class);
				JSONObject currentJson = JSONObject.parseObject(JSON.toJSONString(currentNodeInfo));
				if(null==currentJson){
					currentJson=JSONObject.parseObject("");
				}
				List<String> pkIdList=governServiceDAO.selectPkId();
				nodeInfoDTO.setPk_id(currentJson.getString("id"));
				String listenAddr=currentJson.getString("listenAddr");
				String listenPort=listenAddr.substring(listenAddr.indexOf(":"));
				nodeInfoDTO.setAddr(rpcUrl.get(j).getIp()+listenPort);
				nodeInfoDTO.setBlockNumber(blockHeight);
				nodeInfoDTO.setActive("true");
				if(pkIdList.size()==0|| ! pkIdList.contains(currentJson.getString("id"))){
					governServiceDAO.insertNodeInfo(nodeInfoDTO);
				}else if (pkIdList.size()==0|| pkIdList.contains(currentJson.getString("id"))){
					governServiceDAO.updateActive(nodeInfoDTO);
					governServiceDAO.updateBlocknum(nodeInfoDTO);
					governServiceDAO.updateIp(nodeInfoDTO);
				}
				Object otherNodeInfo = client.invoke(ADMIN_PEERS, null, Object.class);
				JSONArray otherNodegArr = JSONArray.parseArray(JSON.toJSONString(otherNodeInfo));
				if(otherNodegArr==null){
					otherNodegArr=JSONArray.parseArray("[]");
				}
				int jsonSize = otherNodegArr.size();
				List<String> list=governServiceDAO.selectPkId();
				
				for (int i = 0; i < jsonSize; i++) {
					JSONObject otherNodeJson = otherNodegArr.getJSONObject(i);
					nodeInfoDTO = new NodeInfoDTO();
					nodeInfoDTO.setPk_id(otherNodeJson.getString("id"));
					nodeInfoDTO.setAddr(JSONObject.parseObject(otherNodeJson.getString("network")).getString("remoteAddress"));
					nodeInfoDTO.setBlockNumber(Integer.parseInt(otherNodeJson.getString("height").substring(2), 16));
					nodeInfoDTO.setActive("true");
					if(!list.contains(otherNodeJson.getString("id"))){
						governServiceDAO.insertNodeInfo(nodeInfoDTO);
					}else{
						governServiceDAO.updateActive(nodeInfoDTO);
						governServiceDAO.updateBlocknum(nodeInfoDTO);
					}
				}
			} catch (Throwable e) {
				logger.debug("peer Connection refused!");
			}
			
		}
		long endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10005, endtTime - startTime, MSG_MONI_10005);
	}
	
	/**
	 * get node rpc info
	 * 
	 * @param methodName
	 * @param params
	 * @return Object
	 */
	public static Object getInfoByMethod(String methodName, Object[] params)  {
		Object object=null;
		try {
            JsonRpcHttpClient client = null;
            client = new JsonRpcHttpClient(
                    new URL("http://192.168.22.251" + ":" + "8545"));
            try {
                Object currentNodeInfo = client.invoke(ADMIN_NODE_INFO, null,
                        Object.class);
                JSONObject currentJson = JSONObject
                        .parseObject(JSON.toJSONString(currentNodeInfo));
                object = client.invoke(methodName, params, Object.class);
            } catch (Exception e) {
            }
			return object;
		} catch (Throwable e) {
			//logger.error("rpc Exception!!!");
			return object;
		}
	}

	@Autowired
	private GovernServiceDAO governServiceDAO;
	@Autowired
	private BlockChainInfoDTO blockChainInfoDTO;
	@Autowired
	private BlockInfoDTO blockInfoDTO;
	@Autowired
	private TransactionInfoDTO transactionInfoDTO;
	@Autowired
	private ReceiptInfoDTO receiptInfoDTO;
	@Autowired
	private NodeInfoDTO nodeInfoDTO;
}
