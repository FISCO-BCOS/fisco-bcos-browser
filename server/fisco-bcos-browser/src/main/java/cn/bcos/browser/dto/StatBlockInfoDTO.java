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
 * @file: StatBlockInfoDTO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dto;

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
