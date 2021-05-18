package org.bcos.browser.mapper;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.ChainContract;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChainContractMapper {

    void addChainContract(ChainContract contract);

    int getChainContractCount(@Param("groupId")int groupId, @Param("contractAddress") String contractAddress);

    List<ChainContract> getChainContractList(@Param("groupId") int groupId,@Param("contractAddress") String contractAddress, @Param("start")int start, @Param("pageSize")int pageSize);
}
