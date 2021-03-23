package org.bcos.browser.service;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.bcos.browser.util.JsonTools;
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
    @Autowired
    GroupService groupService;

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
    public BasePageResponse getBlockInfoByPage(int groupId, int pageNumber, int pageSize,
            String blockHash, String blockNumber) throws BaseException {
        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);

        // check group id
        groupService.checkGroupId(groupId);
        // check blockNumber
        String number = CommonUtils.trimSpaces(blockNumber);
        if (!StringUtils.isBlank(number)
                && Integer.parseInt(number) > web3j.getBlockNumber(groupId)) {
            throw new BaseException(ConstantCode.NUMBER_TALLER_THAN_LATEST);
        }

        int start = Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", groupId);
        map.put("blockHash", CommonUtils.trimSpaces(blockHash));
        map.put("number", number);
        map.put("start", start);
        map.put("pageSize", pageSize);

        int total = blockMapper.getAllBlockCount(map);

        List<RspGetBlock> list = new ArrayList<RspGetBlock>();
        if (total > 0) {
            List<Block> blockInfoList = blockMapper.getBlockInfoByBage(map);
            for (Block blockInfo : blockInfoList) {
                String sealer = "0x0";
                RspGetBlock rspEntity = new RspGetBlock();
                rspEntity.setBlockHash(blockInfo.getBlockHash());
                rspEntity.setNumber(blockInfo.getNumber());
                if (blockInfo.getNumber() != 0) {
                    List<String> sealerList = JsonTools.stringToObj(blockInfo.getSealerList(),
                            new TypeReference<List<String>>() {});
                    sealer = sealerList.get(CommonUtils.parseHexStr2Int(blockInfo.getSealer()));
                }
                rspEntity.setSealer(sealer);
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
            response.setData(JsonTools.stringToJsonNode(JsonTools.toJSONString(result)));
        } else {
            throw new BaseException(ConstantCode.NODE_ABNORMAL);
        }
        log.debug("###getBlockInfoByHash response:{}###", response);
        return response;
    }
}
