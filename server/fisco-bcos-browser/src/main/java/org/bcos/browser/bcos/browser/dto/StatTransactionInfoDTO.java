package org.bcos.browser.bcos.browser.dto;

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
