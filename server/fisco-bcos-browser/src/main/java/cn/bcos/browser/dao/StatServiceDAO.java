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
 * @file: StatServiceDAO.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bcos.browser.dto.SingleStatInfoDTO;
import cn.bcos.browser.dto.StatBlockInfoDTO;
import cn.bcos.browser.dto.StatTransactionInfoDTO;

@Service
@Transactional
public interface StatServiceDAO {
	
	public void insertSingleStatInfo(SingleStatInfoDTO singleStatInfo);
	
	public void insertStatTransactionInfo(StatTransactionInfoDTO statTransactionInfoDTO);
	
	public void insertStatBlockInfo(StatBlockInfoDTO statBlockInfoDTO);
	
	public List<StatTransactionInfoDTO> selectAllStatTransaction(@Param("tableName")String tableName);
	
	public List<StatBlockInfoDTO> selectAllBlock(@Param("tableName")String tableName);
	
}
