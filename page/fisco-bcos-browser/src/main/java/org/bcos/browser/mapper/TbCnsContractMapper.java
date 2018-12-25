package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbCnsContractDto;
import org.apache.ibatis.annotations.Param;

public interface TbCnsContractMapper {
    public TbCnsContractDto getContractAInfoByAddress(@Param(value = "contractAddress") String contractAddress);

}
