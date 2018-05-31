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
 * @file: Demo.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.test;

import java.sql.Timestamp;

import cn.bcos.browser.dto.TransactionInfoDTO;

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

