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
 * @file: TxnByDayInfoDTO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

/**
 * daily tranding volume information
 */
@Component
public class TxnByDayInfoDTO {
	private Date pk_date;
	private long txn;
	
	public Date getPk_date() {
		return pk_date;
	}
	public void setPk_date(Date pk_date) {
		this.pk_date = pk_date;
	}
	public long getTxn() {
		return txn;
	}
	public void setTxn(long txn) {
		this.txn = txn;
	}
	
}
