package org.bcos.browser.service;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Block;
import org.bcos.browser.entity.rsp.RspGetBlock;
import org.bcos.browser.mapper.BlockMapper;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.DateTimeUtils;
import org.bcos.browser.util.Web3jRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlockService {
    @Autowired
    BlockMapper blockMapper;
    @Autowired
    Web3jRpc web3j;

    /**
     * getBlockInfoByPage.
     * 
     * @param groupId groupId
     * @param pageNumber pageNumber
     * @param pageSize pageSize
     * @param blockHash blockHash
     * @param blockNumber blockNumber
     * @return
     */
    public BasePageResponse getBlockInfoByPage(int groupId, int pageNumber,
            int pageSize, String blockHash, String blockNumber) {
        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);

        int start =
                Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", groupId);
        map.put("blockHash", CommonUtils.trimSpaces(blockHash));
        map.put("number", CommonUtils.trimSpaces(blockNumber));
        map.put("start", start);
        map.put("pageSize", pageSize);
        
        int total = blockMapper.getAllBlockCount(map);

        List<RspGetBlock> list = new ArrayList<RspGetBlock>();
        if (total > 0) {
            List<Block> blockInfoList = blockMapper.getBlockInfoByBage(map);
            for (Block blockInfo : blockInfoList) {
                RspGetBlock rspEntity = new RspGetBlock();
                rspEntity.setBlockHash(blockInfo.getBlockHash());
                rspEntity.setNumber(blockInfo.getNumber());
                rspEntity.setSealer(blockInfo.getSealer());
                rspEntity.setDateTimeStr(DateTimeUtils.timestamp2String(blockInfo.getBlockTime(),
                        Constants.DEFAULT_DATA_TIME_FORMAT));
                rspEntity.setTxn(blockInfo.getTxn());
                list.add(rspEntity);
            }
            response.setData(list);
            response.setTotalCount(total);
        }
        return response;
    }

    /**
     * getBlockInfoByHash.
     * 
     * @param groupId groupId
     * @param blockHash blockHash
     * @return
     * @throws BaseException 
     */
    public BaseResponse getBlockInfoByHash(int groupId, String blockHash) throws BaseException {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        Object[] params = new Object[] {groupId, blockHash, true};
        Object result = web3j.rpcRequest(groupId, Constants.GET_BLOCK_BY_HASH, params);
        if (result != null) {
            response.setData(JSON.parse(JSON.toJSONString(result)));
        } else {
        	throw new BaseException(ConstantCode.NODE_ABNORMAL);
        }
        log.debug("###getBlockInfoByHash response:{}###", response);
        return response;
    }
}
