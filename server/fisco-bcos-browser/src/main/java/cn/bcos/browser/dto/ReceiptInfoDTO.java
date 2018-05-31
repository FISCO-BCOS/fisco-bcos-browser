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
 * @file: ReceiptInfoDTO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dto;

import org.springframework.stereotype.Component;

/**
 * transaction receipt information
 */
@Component
public class ReceiptInfoDTO {
	private String pk_hash;
	private String blockHash;
	private int blockNumber;
	private String contractAddress;
	private long transactionIndex;
	private long gasUsed;
	private long cumulativeGasUsed;
	private String logs;
	private String detailInfo;
	
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
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public long getTransactionIndex() {
		return transactionIndex;
	}
	public void setTransactionIndex(long transactionIndex) {
		this.transactionIndex = transactionIndex;
	}
	public long getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(long gasUsed) {
		this.gasUsed = gasUsed;
	}
	public long getCumulativeGasUsed() {
		return cumulativeGasUsed;
	}
	public void setCumulativeGasUsed(long cumulativeGasUsed) {
		this.cumulativeGasUsed = cumulativeGasUsed;
	}
	public String getLogs() {
		return logs;
	}
	public void setLogs(String logs) {
		this.logs = logs;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
}
