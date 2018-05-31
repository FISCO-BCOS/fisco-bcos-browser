package org.bcos.browser.test;

import java.sql.Timestamp;

import org.bcos.browser.dto.TransactionInfoDTO;

public class Demo {
	
	public static void main(String[] args) {
		
		String lastBlock = "0x74d0";
		long blockHeight = Long.parseLong(lastBlock.substring(2), 16);
		long temp = Long.parseLong("1508314804000");
		
		TransactionInfoDTO transactionInfoDTO = new TransactionInfoDTO();
		transactionInfoDTO.setBlockTimestamp(new Timestamp(blockHeight));
		System.out.println(blockHeight);
		System.out.println(new Timestamp(blockHeight));
		System.out.println(transactionInfoDTO.getBlockTimestamp());
		System.out.println(new Timestamp(System.currentTimeMillis()));
		System.out.println(new Timestamp(temp));
	}

}

