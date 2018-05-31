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
 * @file: SingleStatInfoDTO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dto;

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
