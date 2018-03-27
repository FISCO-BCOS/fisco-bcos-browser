package org.bcos.browser.bcos.browser.dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

/**
 * single stat information
 */
@Component
public class SingleStatInfoDTO {
	private String object;
	private String attr;
	private String attrName;
	private Timestamp collectTimestamp;
	private double metricValue;
	private String hostIp;
	private String detailInfo;
	
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public Timestamp getCollectTimestamp() {
		return collectTimestamp;
	}
	public void setCollectTimestamp(Timestamp collectTimestamp) {
		this.collectTimestamp = collectTimestamp;
	}
	public double getMetricValue() {
		return metricValue;
	}
	public void setMetricValue(double metricValue) {
		this.metricValue = metricValue;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	
}
