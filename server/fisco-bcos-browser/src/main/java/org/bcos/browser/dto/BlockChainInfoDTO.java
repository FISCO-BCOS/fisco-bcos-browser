package org.bcos.browser.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/**
 * 区块链信息
 */
@Component
public class BlockChainInfoDTO {
	private int lastBlock;
	private int txn;
	private long pendingTxn;
	private long pbftView;
	private BigDecimal avgTime;
	
	public int getLastBlock() {
		return lastBlock;
	}
	public void setLastBlock(int lastBlock) {
		this.lastBlock = lastBlock;
	}
	public int getTxn() {
		return txn;
	}
	public void setTxn(int txn) {
		this.txn = txn;
	}
	public long getPendingTxn() {
		return pendingTxn;
	}
	public void setPendingTxn(long pendingTxn) {
		this.pendingTxn = pendingTxn;
	}
	public long getPbftView() {
		return pbftView;
	}
	public void setPbftView(long pbftView) {
		this.pbftView = pbftView;
	}
	public BigDecimal getAvgTime() {
		return avgTime;
	}
	public void setAvgTime(BigDecimal avgTime) {
		this.avgTime = avgTime;
	}
	
}
