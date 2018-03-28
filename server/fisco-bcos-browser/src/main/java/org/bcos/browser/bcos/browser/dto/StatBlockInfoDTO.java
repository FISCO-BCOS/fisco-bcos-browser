package org.bcos.browser.bcos.browser.dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

/**
 * block process information
 */
@Component
public class StatBlockInfoDTO {
	private int height;
	private String object;
	private Timestamp collectTimestamp;
	private String hash;
	private String start;
	private String sealed;
	private String execed;
	private String signed;
	private String commited;
	private String onChain;
	private String viewchange_start;
	private String viewchanged;
	private String detailInfo;
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
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
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getSealed() {
		return sealed;
	}
	public void setSealed(String sealed) {
		this.sealed = sealed;
	}
	public String getExeced() {
		return execed;
	}
	public void setExeced(String execed) {
		this.execed = execed;
	}
	public String getSigned() {
		return signed;
	}
	public void setSigned(String signed) {
		this.signed = signed;
	}
	public String getCommited() {
		return commited;
	}
	public void setCommited(String commited) {
		this.commited = commited;
	}
	public String getOnChain() {
		return onChain;
	}
	public void setOnChain(String onChain) {
		this.onChain = onChain;
	}
	public String getViewchange_start() {
		return viewchange_start;
	}
	public void setViewchange_start(String viewchange_start) {
		this.viewchange_start = viewchange_start;
	}
	public String getViewchanged() {
		return viewchanged;
	}
	public void setViewchanged(String viewchanged) {
		this.viewchanged = viewchanged;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
}
