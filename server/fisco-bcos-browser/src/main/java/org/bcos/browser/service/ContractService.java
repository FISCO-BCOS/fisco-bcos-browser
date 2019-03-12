package org.bcos.browser.service;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Contract;
import org.bcos.browser.entity.req.ReqContracts;
import org.bcos.browser.mapper.ContractMapper;
import org.bcos.browser.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContractService {
    @Autowired
    ContractMapper contractMapper;

    /**
     * addContract.
     * 
     * @param contracts info
     * @return
     */
    public BaseResponse addContract(ReqContracts contracts) throws BaseException {
        log.info("addContract contracts:{}", contracts);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        for (Contract loop : contracts.getData()) {
            loop.setGroupId(contracts.getGroupId());
            int count = contractMapper.getContractByName(contracts.getGroupId(), 
                    loop.getContractName());
            if (count > 0) {
                loop.setContractName(loop.getContractName() + "_" + CommonUtils.getDateDescStr());;
            }
            contractMapper.add(loop);
        }
        return response;
    }

    /**
     * getContractList.
     * 
     * @return
     */
    public BasePageResponse getContractList(int groupId, int pageNumber, int pageSize) {
        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);
        int start =
                Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);
        int total = contractMapper.getContractCnts(groupId);
        List<Contract> list = contractMapper.getContractList(groupId, start, pageSize);
        response.setTotalCount(total);
        response.setData(list);
        return response;
    }

    /**
     * updateContractList.
     * 
     * @param contracts info
     * @return
     */
    public BaseResponse updateContract(ReqContracts contracts) {
        log.info("updateContract contracts:{}", contracts);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        for (Contract loop : contracts.getData()) {
            loop.setGroupId(contracts.getGroupId());
            if (StringUtils.isBlank(loop.getContractAbi())) {
                loop.setContractStatus(2);
            } else {
                loop.setContractStatus(1);
            }
            contractMapper.updateContract(loop);
        }
        return response;
    }

    /**
     * deleteContract.
     * 
     * @param groupId groupId
     * @param contractId contractId
     * @return
     */
    public BaseResponse deleteContract(int groupId, int contractId) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        contractMapper.deleteContract(groupId, contractId);
        return response;
    }
}
