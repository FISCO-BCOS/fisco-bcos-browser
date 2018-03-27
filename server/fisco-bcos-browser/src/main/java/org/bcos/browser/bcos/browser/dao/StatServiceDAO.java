package org.bcos.browser.bcos.browser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.bcos.browser.dto.SingleStatInfoDTO;
import org.bcos.browser.bcos.browser.dto.StatBlockInfoDTO;
import org.bcos.browser.bcos.browser.dto.StatTransactionInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface StatServiceDAO {
	
	public void insertSingleStatInfo(SingleStatInfoDTO singleStatInfo);
	
	public void insertStatTransactionInfo(StatTransactionInfoDTO statTransactionInfoDTO);
	
	public void insertStatBlockInfo(StatBlockInfoDTO statBlockInfoDTO);
	
	public List<StatTransactionInfoDTO> selectAllStatTransaction(@Param("tableName")String tableName);
	
	public List<StatBlockInfoDTO> selectAllBlock(@Param("tableName")String tableName);
	
}
