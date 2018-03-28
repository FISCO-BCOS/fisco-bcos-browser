package org.bcos.browser.bcos.browser.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

/**
 * transaction information
 */
@Component
public class TransactionInfoDTO {
	private String pk_hash;
	private String blockHash;
	private int blockNumber;
	private Timestamp blockTimestamp;
	private long blockGasLimit;
	private long transactionIndex;
	private String transactionFrom;
	private String transactionTo;
	private long gas;
	private BigDecimal gasPrice;
	private long cumulativeGas;
	private String randomId;
	private String inputText;
	private String type;
	private String contractName ;
	private String version;
	private String method;
	private String params;
	

	public String getPk_hash() {
		return pk_hash;
	}
	public void setPk_hash(String pk_hash) {
		this.pk_hash = pk_hash;
	}
	public String getBlockHash() {
		return blockHash;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public int getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}
	public Timestamp getBlockTimestamp() {
		return blockTimestamp;
	}
	public void setBlockTimestamp(Timestamp blockTimestamp) {
		this.blockTimestamp = blockTimestamp;
	}
	public long getBlockGasLimit() {
		return blockGasLimit;
	}
	public void setBlockGasLimit(long blockGasLimit) {
		this.blockGasLimit = blockGasLimit;
	}
	public long getTransactionIndex() {
		return transactionIndex;
	}
	public void setTransactionIndex(long transactionIndex) {
		this.transactionIndex = transactionIndex;
	}
	public String getTransactionFrom() {
		return transactionFrom;
	}
	public void setTransactionFrom(String transactionFrom) {
		this.transactionFrom = transactionFrom;
	}
	public String getTransactionTo() {
		return transactionTo;
	}
	public void setTransactionTo(String transactionTo) {
		this.transactionTo = transactionTo;
	}
	public long getGas() {
		return gas;
	}
	public void setGas(long gas) {
		this.gas = gas;
	}
	public BigDecimal getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(BigDecimal gasPrice) {
		this.gasPrice = gasPrice;
	}
	public long getCumulativeGas() {
		return cumulativeGas;
	}
	public void setCumulativeGas(long cumulativeGas) {
		this.cumulativeGas = cumulativeGas;
	}
	public String getRandomId() {
		return randomId;
	}
	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}
	public String getInputText() {
		return inputText;
	}
	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	
}
