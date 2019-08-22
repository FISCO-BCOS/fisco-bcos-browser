package org.bcos.browser.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.Contract;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractMapper {

    void add(Contract contract);

    int getContractByName(@Param(value = "contractName") String contractName);

    int getContractByNameAndPath(
            @Param(value = "contractName") String contractName,
            @Param(value = "contractPath") String contractPath);

    int getContractCnts();
    
    List<Contract> getContractList(@Param(value = "start") int start,
            @Param(value = "pageSize") int pageSize);
    
    Contract getAbiByInput(@Param(value = "input") String input);

    void updateContract(Contract contract);

    void deleteContract(@Param(value = "contractId") int contractId);
}
