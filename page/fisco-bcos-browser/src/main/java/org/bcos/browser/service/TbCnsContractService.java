package org.bcos.browser.service;



import org.bcos.browser.dto.TbCnsContractDto;
import org.apache.ibatis.annotations.Param;

public interface TbCnsContractService {
    public TbCnsContractDto getContractAInfoByAddress(@Param("warrantID")String contractAddress);
}
