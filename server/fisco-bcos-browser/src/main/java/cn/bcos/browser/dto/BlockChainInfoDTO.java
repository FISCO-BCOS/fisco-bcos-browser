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
 * @file: BlockChainInfoDTO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/**
 *  fisco-bcos information
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
