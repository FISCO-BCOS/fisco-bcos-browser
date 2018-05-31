package org.bcos.browser.service;

import static org.bcos.browser.util.Constants.*;
import static org.bcos.browser.util.LogUtils.getMonitorLogger;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import org.bcos.browser.dao.GovernServiceDAO;
import org.bcos.browser.dto.BlockChainInfoDTO;
import org.bcos.browser.dto.BlockInfoDTO;
import org.bcos.browser.dto.ConfigInfoDDO;
import org.bcos.browser.dto.NodeInfoDTO;
import org.bcos.browser.dto.ReceiptInfoDTO;
import org.bcos.browser.dto.TransactionInfoDTO;

/**
 * 区块链治理服务
 */
@Component
public class GovernService {
	private Logger logger = LoggerFactory.getLogger(GovernService.class);

	public void start() throws Throwable {
		handleBlockChainInfo();
	}

	/**
	 * 处理区块链全局信息
	 * @throws Throwable 
	 */
	public void handleBlockChainInfo() throws Throwable {
		long startTime = System.currentTimeMillis();
		// 获取当前块高
		String lastBlock = (String) getInfoByMethod(ETH_BLOCK_NUMBER, null);
		int blockHeight = Integer.parseInt(lastBlock.substring(2), 16);
		logger.debug("###handleBlockChainInfo 最新块高：{}###", blockHeight);
		if (blockHeight == 0) {
			return;
		} 
		
		// 获取db块高
		int blockChainHeigth = governServiceDAO.selectBlockChainHeigth();
		logger.debug("###handleBlockChainInfo db块高：{}###", blockChainHeigth);
		
		// 获取pbftView
		String pbftView = (String) getInfoByMethod(ETH_PBFT_VIEW, null);
		// 获取交易数
		int txn = governServiceDAO.selectTransactionInfoCount();
		// 获取未交易数
		int pendingTxn = governServiceDAO.selectPendingTransInfoCount();
		// 更新全局信息
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
		
		// 更新节点信息
		handleNodeInfo();
		
		long endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10000, endtTime - startTime, MSG_MONI_10000);
	}
	
	/**
	 * 处理区块信息
	 * @throws Throwable 
	 */
	public void handleBlockInfo() throws Throwable {
		long startTime = System.currentTimeMillis();
		// 获取当前块高
		String lastBlock = (String) getInfoByMethod(ETH_BLOCK_NUMBER, null);
		int blockHeight = Integer.parseInt(lastBlock.substring(2), 16);
//		int blockHeight = 3;
		logger.debug("###handleBlockInfo 最新块高：{}###", blockHeight);
		// 获取db块高
		long startTime1 = System.currentTimeMillis();
		int dbBlockHeight = governServiceDAO.selectBlockHeigth();
		long endtTime1 = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10004, endtTime1 - startTime1, MSG_MONI_10004);
		logger.debug("###handleBlockInfo db块高：{}###", dbBlockHeight);
		
		long endtTime = 0;
		if (blockHeight == 0 || blockHeight == dbBlockHeight) {
			endtTime = System.currentTimeMillis();
			getMonitorLogger().info(CODE_MONI_10001, endtTime - startTime, MSG_MONI_10001);
			return;
		} else {
			// 处理block信息
			for (int i = dbBlockHeight + 1; i <= blockHeight; i++) {
				if (i == 1) {
					handleBlockInfo(0);
					handleBlockInfo(1);
				} else {
					handleBlockInfo(i);
				}
			}
			// 处理每日交易量记录
			governServiceDAO.insertTxnByDayInfo();
		}
		endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10001, endtTime - startTime, MSG_MONI_10001);
		
	}

	/**
	 * 根据高度处理各个区块信息
	 * 
	 * @param blockHeight
	 * @throws Throwable 
	 */
	public void handleBlockInfo(int blockHeight) throws Throwable {
		// 根据块高获取block信息
		Object[] params = new Object[] { String.valueOf(blockHeight), true };
		Object blockInfo = getInfoByMethod(ETH_GET_BLOCK_BY_NUMBER, params);
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(blockInfo));
		logger.debug("###blockInfo：{}###", json);

		// 处理交易信息
//		long startTime = System.currentTimeMillis();
		Map<String, Object> map = handleTransInfo(json);
//		long endtTime = System.currentTimeMillis();
//		getMonitorLogger().info(CODE_MONI_10002, endtTime - startTime, MSG_MONI_10002);
		BigDecimal gasPriceTotal = new BigDecimal(map.get("gasPriceTotal").toString());
		BigDecimal transCount = new BigDecimal(map.get("transCount").toString());

		// 添加区块信息
		blockInfoDTO = new BlockInfoDTO();
		blockInfoDTO.setPk_hash(json.getString("hash"));
		blockInfoDTO.setNumber(Integer.parseInt(json.getString("number").substring(2), 16));
		blockInfoDTO.setParentHash(json.getString("parentHash"));
		blockInfoDTO.setMiner(json.getString("miner"));
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
	}

	/**
	 * 处理各个交易信息
	 * 
	 * @param json
	 * @return Map<String, Object>
	 * @throws Throwable 
	 */
	public Map<String, Object> handleTransInfo(JSONObject json) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		long gasPriceTotal = 0;
		// 获取交易信息
		JSONArray jsonArr = json.getJSONArray("transactions");
		logger.debug("###transactions：{}###", jsonArr);
		long jsonSize = jsonArr.size();
		for (int j = 0; j < jsonSize; j++) {
			JSONObject jsonTrans = jsonArr.getJSONObject(j);
			gasPriceTotal = gasPriceTotal + Long.parseLong(jsonTrans.getString("gasPrice").substring(2), 16);
			// 添加交易信息
			transactionInfoDTO = new TransactionInfoDTO();
			transactionInfoDTO.setPk_hash(jsonTrans.getString("hash"));
			transactionInfoDTO.setBlockHash(jsonTrans.getString("blockHash"));
			transactionInfoDTO.setBlockNumber(Integer.parseInt(jsonTrans.getString("blockNumber").substring(2), 16));
			if ("0".equals(json.getString("timestamp").substring(2))) {
				transactionInfoDTO.setBlockTimestamp(null);
			} else {
				transactionInfoDTO.setBlockTimestamp(new Timestamp(Long.parseLong(json.getString("timestamp").substring(2), 16)));
			}
			transactionInfoDTO.setBlockGasLimit(Long.parseLong(json.getString("gasLimit").substring(2), 16));
			transactionInfoDTO.setTransactionIndex(Long.parseLong(jsonTrans.getString("transactionIndex").substring(2), 16));
			transactionInfoDTO.setTransactionFrom(jsonTrans.getString("from"));
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
			governServiceDAO.insertTransactionInfo(transactionInfoDTO);
			
			// 处理交易回执信息
			handleTransReceiptInfo(jsonTrans.getString("hash"));
		}

		map.put("transCount", jsonSize);
		map.put("gasPriceTotal", gasPriceTotal);
		return map;
	}
	
	/**
	 * 处理交易回执信息
	 * @throws Throwable 
	 */
	public void handleTransReceiptInfo(String hash) throws Throwable {
		Object[] params = new Object[] { hash };
		Object receiptInfo = getInfoByMethod(ETH_GET_TRANSACTION_RECEIPT, params);
		JSONObject receiptJson = JSONObject.parseObject(JSON.toJSONString(receiptInfo));
		// 添加交易回执信息
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

	}

	/**
	 * 处理未上链的交易数据
	 * @throws Throwable 
	 */
	public void handlePendingTransInfo() throws Throwable {
		long startTime = System.currentTimeMillis();
		// 清空未上链交易信息
		governServiceDAO.deletePendingTransInfo();
		// 获取未上链的交易
		Object pendingTransactions = getInfoByMethod(ETH_PENDING_TRANSACTIONS, null);
		logger.debug("###pendingTransactions：{}###", pendingTransactions);
		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(pendingTransactions));
		handlePendingTransArr(jsonObject, "current");
		handlePendingTransArr(jsonObject, "pending");
		long endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10003, endtTime - startTime, MSG_MONI_10003);
	}
	
	/**
	 * 添加未上链交易信息
	 * @param jsonObject
	 * @param type
	 */
	public void handlePendingTransArr(JSONObject jsonObject, String type) {
		JSONArray jsonArr = jsonObject.getJSONArray(type);
		int jsonSize = jsonArr.size();
		for (int i = 0; i < jsonSize; i++) {
			JSONObject jsonCurrent = jsonArr.getJSONObject(i);
			// 添加未上链交易信息
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
	 * 处理节点信息
	 * @throws Throwable 
	 */
	public void handleNodeInfo() throws Throwable {
		long startTime = System.currentTimeMillis();
		// 先清空节点信息
		governServiceDAO.deleteNodeInfo();
		
		// 获取当前块高
		String lastBlock = (String) getInfoByMethod(ETH_BLOCK_NUMBER, null);
		int blockHeight = Integer.parseInt(lastBlock.substring(2), 16);
		logger.debug("###handleNodeInfo 最新块高：{}###", blockHeight);
		
		Object currentNodeInfo = getInfoByMethod(ADMIN_NODE_INFO, null);
		JSONObject currentJson = JSONObject.parseObject(JSON.toJSONString(currentNodeInfo));
		nodeInfoDTO.setPk_id(currentJson.getString("id"));
		nodeInfoDTO.setAddr(currentJson.getString("listenAddr"));
		nodeInfoDTO.setBlockNumber(blockHeight);
		governServiceDAO.insertNodeInfo(nodeInfoDTO);
		
		Object otherNodeInfo = getInfoByMethod(ADMIN_PEERS, null);
		JSONArray otherNodegArr = JSONArray.parseArray(JSON.toJSONString(otherNodeInfo));
		int jsonSize = otherNodegArr.size();
		for (int i = 0; i < jsonSize; i++) {
			JSONObject otherNodeJson = otherNodegArr.getJSONObject(i);
			nodeInfoDTO = new NodeInfoDTO();
			nodeInfoDTO.setPk_id(otherNodeJson.getString("id"));
			nodeInfoDTO.setAddr(JSONObject.parseObject(otherNodeJson.getString("network")).getString("remoteAddress"));
			nodeInfoDTO.setBlockNumber(Integer.parseInt(otherNodeJson.getString("height").substring(2), 16));
			governServiceDAO.insertNodeInfo(nodeInfoDTO);
		}
		
		long endtTime = System.currentTimeMillis();
		getMonitorLogger().info(CODE_MONI_10005, endtTime - startTime, MSG_MONI_10005);
	}
	

	/**
	 * 根据rpc获取节点信息
	 * 
	 * @param methodName
	 * @param params
	 * @return Object
	 * @throws Throwable 
	 */
	public Object getInfoByMethod(String methodName, Object[] params) throws Throwable {
		try {
			// 区块链节点地址
			String url = configInfo.getUrl();
			JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(url));
			Object object = client.invoke(methodName, params, Object.class);
			return object;
		} catch (Throwable e) {
			logger.error("请求RPC异常", e);
			throw e;
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
	@Autowired
	private ConfigInfoDDO configInfo;
}
