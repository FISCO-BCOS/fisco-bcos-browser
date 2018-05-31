package org.bcos.browser.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

/**
 * 区块信息
 */
@Component
public class BlockInfoDTO {
	private String pk_hash;
	private int number;
	private String parentHash;
	private String miner;
	private int genIndex;
	private int size;
	private long gasLimit;
	private long gasUsed;
	private BigDecimal avgGasPrice;
	private Timestamp timestamp;
	private long txn;
	private String extraData;
	private String detailInfo;
	
	public String getPk_hash() {
		return pk_hash;
	}
	public void setPk_hash(String pk_hash) {
		this.pk_hash = pk_hash;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getParentHash() {
		return parentHash;
	}
	public void setParentHash(String parentHash) {
		this.parentHash = parentHash;
	}
	public String getMiner() {
		return miner;
	}
	public void setMiner(String miner) {
		this.miner = miner;
	}
	public int getGenIndex() {
		return genIndex;
	}
	public void setGenIndex(int genIndex) {
		this.genIndex = genIndex;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getGasLimit() {
		return gasLimit;
	}
	public void setGasLimit(long gasLimit) {
		this.gasLimit = gasLimit;
	}
	public long getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(long gasUsed) {
		this.gasUsed = gasUsed;
	}
	public BigDecimal getAvgGasPrice() {
		return avgGasPrice;
	}
	public void setAvgGasPrice(BigDecimal avgGasPrice) {
		this.avgGasPrice = avgGasPrice;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public long getTxn() {
		return txn;
	}
	public void setTxn(long txn) {
		this.txn = txn;
	}
	public String getExtraData() {
		return extraData;
	}
	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	
}
