package org.bcos.browser.bcos.browser.dto;

import org.springframework.stereotype.Component;

/**
 * 
 * node rpc information
 *
 */
 
@Component
public class PeerRpcDTO {
	
	private int pk_id;
	
	private String ip;
	
	private int rpcPort;

	public int getPk_id() {
		return pk_id;
	}

	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getRpcPort() {
		return rpcPort;
	}

	public void setRpcPort(int rpcPort) {
		this.rpcPort = rpcPort;
	}
	
}
