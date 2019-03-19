package org.bcos.browser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.ReceiptFromChain;
import org.bcos.browser.entity.dto.Transaction;
import org.bcos.browser.entity.dto.TransactionAndReceipt;
import org.bcos.browser.entity.dto.TransactionFromChain;
import org.bcos.browser.entity.req.ReqGetCode;
import org.bcos.browser.entity.req.ReqTransaction;
import org.bcos.browser.entity.rsp.RspGetTransaction;
import org.bcos.browser.mapper.NodeMapper;
import org.bcos.browser.mapper.TransactionMapper;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.DateTimeUtils;
import org.bcos.browser.util.Web3jRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    Web3jRpc web3jRpc;
    @Autowired
    NodeMapper nodeMapper;

    /**
     * getTransInfoByPage.
     * 
     * @param groupId groupId
     * @param pageNumber pageNumber
     * @param pageSize pageSize
     * @param transHash transHash
     * @param blockNumber blockNumber
     * @return
     */
    public BasePageResponse getTransInfoByPage(int groupId, int pageNumber,
            int pageSize, String transHash, String blockNumber) {
        log.info("analyzeData groupId:{} pageNumber:{} pageSize:{} transHash:{} blockNumber:{}",
                groupId, pageNumber, pageSize, transHash, blockNumber);
        int start =
                Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", groupId);
        map.put("transHash", CommonUtils.trimSpaces(transHash));
        map.put("number", CommonUtils.trimSpaces(blockNumber));
        map.put("start", start);
        map.put("pageSize", pageSize);

        int total = transactionMapper.getAllTransactionCount(map);

        List<RspGetTransaction> list = new ArrayList<>();
        if (total > 0) {
            List<Transaction> listTbTransactionDto = transactionMapper.getTbTransactionByPage(map);
            if (listTbTransactionDto != null) {
                for (Transaction tbTransactionDto : listTbTransactionDto) {
                    RspGetTransaction rspEntity = new RspGetTransaction();
                    rspEntity.setTransHash(tbTransactionDto.getTransHash());
                    rspEntity.setBlockHash(tbTransactionDto.getBlockHash());
                    rspEntity.setBlockNumber(tbTransactionDto.getBlockNumber());
                    rspEntity.setBlockTimesStr(DateTimeUtils.timestamp2String(
                            tbTransactionDto.getBlockTime(), Constants.DEFAULT_DATA_TIME_FORMAT));
                    rspEntity.setFrom(tbTransactionDto.getTransFrom());
                    rspEntity.setTo(tbTransactionDto.getTransTo());
                    rspEntity.setTransIndex(tbTransactionDto.getTransIndex());
                    rspEntity.setMethod(tbTransactionDto.getMethod());
                    list.add(rspEntity);
                }
            }
        }
        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);
        response.setTotalCount(total);
        response.setData(list);
        return response;
    }

    /**
     * analyzeData.
     * 
     * @param reqTransaction info
     * @return
     */
    public BaseResponse analyzeData(ReqTransaction reqTransaction) {
        log.info("analyzeData reqTransaction:{}", reqTransaction);
        List<Transaction> transHashList = reqTransaction.getData();
        int groupId = reqTransaction.getGroupId();
        List<TransactionAndReceipt> data = new ArrayList<>();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", groupId);
        map.put("type", 0);
        int total = nodeMapper.getNodeCnts(map);
        // check for manually added nodes
        if (total > 0) {
            for (int i = 0; i < transHashList.size(); i++) {
                TransactionAndReceipt result = new TransactionAndReceipt();
                TransactionFromChain transInfo = web3jRpc.getTransByHash(groupId, 
                        transHashList.get(i).getTransHash());
                ReceiptFromChain receiptInfo = web3jRpc.getReceiptByHash(groupId, 
                        transHashList.get(i).getTransHash());
                result.setTransactionFromChain(transInfo);
                result.setReceiptFromChain(receiptInfo);
                data.add(result);
            }
        }
        
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        response.setData(data);
        log.debug("###analyzeData response:{}###", response);
        return response;
    }

    /**
     * updateMethod.
     * 
     * @param reqTransaction info
     * @return
     */
    public BaseResponse updateMethod(ReqTransaction reqTransaction) {
        log.info("updateTransInfo reqTransaction:{}", reqTransaction);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        for (Transaction transaction : reqTransaction.getData()) {
            transaction.setGroupId(reqTransaction.getGroupId());
            transactionMapper.updateMethod(transaction);
        }
        return response;
    }

    /**
     * getTransactionByHash.
     * 
     * @param groupId groupId
     * @param transHash transHash
     * @return
     */
    public BaseResponse getTransactionByHash(int groupId, String transHash) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        TransactionFromChain info = web3jRpc.getTransByHash(groupId, transHash);
        response.setData(info);
        log.debug("###getTransactionByHash response:{}###", response);
        return response;
    }

    /**
     * getTransactionReceiptByHash.
     * 
     * @param groupId groupId
     * @param transHash transHash
     * @return
     */
    public BaseResponse getReceiptByHash(int groupId, String transHash) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        ReceiptFromChain info = web3jRpc.getReceiptByHash(groupId, transHash);
        response.setData(info);
        log.debug("###getTransactionReceiptByHash response:{}###", response);
        return response;
    }

    /**
     * getCode.
     * 
     * @param reqGetCode info
     * @return
     */
    public BaseResponse getCode(ReqGetCode reqGetCode) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < reqGetCode.getData().size(); i++) {
            String code = web3jRpc.getCode(reqGetCode.getGroupId(), 
                    reqGetCode.getData().get(i));
            data.add(code);
        }
        
        response.setData(data);
        log.debug("###getCode response:{}###", response);
        return response;
    }
}
