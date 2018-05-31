package org.bcos.browser.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

/**
 * 每日交易量信息
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
