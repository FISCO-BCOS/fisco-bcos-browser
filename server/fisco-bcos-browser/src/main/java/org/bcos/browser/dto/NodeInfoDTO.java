package org.bcos.browser.dto;

import org.springframework.stereotype.Component;

/**
 * 节点信息
 */
@Component
public class NodeInfoDTO {
	private String pk_id;
	private String addr;
	private int blockNumber;
	
	public String getPk_id() {
		return pk_id;
	}
	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}
}
