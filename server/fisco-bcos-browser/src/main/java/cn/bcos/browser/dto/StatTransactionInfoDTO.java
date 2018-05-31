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
 * @file: StatTransactionInfoDTO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

/**
 * transaction process information
 */
@Component
public class StatTransactionInfoDTO {
	private String hash;
	private String object;
	private Timestamp collectTimestamp;
	private String startMsg;
	private String startTime;
	private String onChainMsg;
	private String onChainTime;
	private String detailInfo;
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public Timestamp getCollectTimestamp() {
		return collectTimestamp;
	}
	public void setCollectTimestamp(Timestamp collectTimestamp) {
		this.collectTimestamp = collectTimestamp;
	}
	public String getStartMsg() {
		return startMsg;
	}
	public void setStartMsg(String startMsg) {
		this.startMsg = startMsg;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getOnChainMsg() {
		return onChainMsg;
	}
	public void setOnChainMsg(String onChainMsg) {
		this.onChainMsg = onChainMsg;
	}
	public String getOnChainTime() {
		return onChainTime;
	}
	public void setOnChainTime(String onChainTime) {
		this.onChainTime = onChainTime;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
}
