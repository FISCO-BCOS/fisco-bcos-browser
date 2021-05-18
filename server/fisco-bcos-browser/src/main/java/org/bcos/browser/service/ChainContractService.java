package org.bcos.browser.service;

import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.dto.ChainContract;
import org.bcos.browser.entity.dto.Transaction;
import org.bcos.browser.entity.rsp.RspGetTransaction;
import org.bcos.browser.mapper.ChainContractMapper;
import org.bcos.browser.mapper.TransactionMapper;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ChainContractService {

    @Autowired
    ChainContractMapper chainContractMapper;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    UserService userService;

    /**
     * getChainContractList.
     *
     * @return
     */
    public BasePageResponse getChainContractList(int groupId, String contractAddress, int pageNumber, int pageSize) {
        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);
        int start = Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);
        int total = chainContractMapper.getChainContractCount(groupId,contractAddress);
        List<ChainContract> list = chainContractMapper.getChainContractList(groupId,contractAddress, start, pageSize);
        for (ChainContract chainContract : list) {
            chainContract.setBlockTimeStr(DateTimeUtils.timestamp2String(
                    chainContract.getBlockTime(), Constants.DEFAULT_DATA_TIME_FORMAT));
        }
        response.setTotalCount(total);
        response.setData(list);
        return response;
    }

    /**
     * getChainContractList.
     *
     * @return
     */
    public BasePageResponse getTransContractList(int groupId, String address, String userParam, int pageNumber, int pageSize) throws BaseException {
        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);
        int start = Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);
        Map<String, Object> param = new HashMap<>();
        param.put("groupId",groupId);
        param.put("to",address);
        param.put("userParam",userParam);
        param.put("start",start);
        param.put("pageSize",pageSize);
        int total = transactionMapper.queryCountByFromOrTo(param);
        List<Transaction> list = transactionMapper.queryTransactionListFromOrTo(param);
        List<RspGetTransaction> rep = new ArrayList<>();
        for (Transaction transaction : list) {
            RspGetTransaction rspEntity = new RspGetTransaction();
            rspEntity.setTransHash(transaction.getTransHash());
            rspEntity.setBlockHash(transaction.getBlockHash());
            rspEntity.setBlockNumber(transaction.getBlockNumber());
            rspEntity.setBlockTimesStr(DateTimeUtils.timestamp2String(
                    transaction.getBlockTime(), Constants.DEFAULT_DATA_TIME_FORMAT));
            rspEntity.setFrom(userService.queryNameByAddress(groupId,
                    transaction.getTransFrom()));
            rspEntity.setTo(transaction.getTransTo());
            rspEntity.setTransIndex(
                    CommonUtils.parseHexStr2Int(transaction.getTransIndex()));
            rep.add(rspEntity);
        }
        log.info("getTransContractList total: {}, list: {}",total, list);
        response.setTotalCount(total);
        response.setData(rep);
        return response;
    }
}
