package org.bcos.browser.service.impl;

import org.bcos.browser.dto.TbBlockDto;
import org.bcos.browser.mapper.TbBlockMapper;
import org.bcos.browser.service.TbBlockService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Description: 每个区块的，服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13
 */
@Service
public class TbBlockServiceImpl implements TbBlockService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbBlockServiceImpl.class);

    @Autowired
    TbBlockMapper tbBlockMapper;

    /**
     *@Description: 查询区块表记录总数
     */
    @Override
    public int getAllBlockCount() {
        LOGGER.info("getAllBlockCount.start...");
        int total = tbBlockMapper.getAllBlockCount();
        LOGGER.info("getAllBlockCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: 分页查询区块表的记录
     */
    @Override
    public List<TbBlockDto> getBlockInfoByOffset(Integer offset, Integer size) {
        LOGGER.info("getBlockInfoByByOffset.start offset:{},size:{}",offset,size);
        List<TbBlockDto> list = tbBlockMapper.getBlockInfoByOffset(offset,size);
        LOGGER.info("getBlockInfoByByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: 根据块高查询块详细信息
     */
    @Override
    public String getBlockDetailInfoByBlockHash(String blockHash) {
        LOGGER.info("getBlockDetailInfoByBlockHash.start blockHash:{}",blockHash);
        TbBlockDto tbBlockDto = tbBlockMapper.getBlockDetailInfoByBlockHash(blockHash);
        LOGGER.info("getBlockDetailInfoByBlockHash blockHash:{},result:{}",blockHash,JSON.toJSONString(tbBlockDto));

        if (tbBlockDto == null){
            LOGGER.warn("query fail:tbBlockDto is null !");
            return null;
        }

        String detailInfo = tbBlockDto.getDetailInfo();
        LOGGER.info("getBlockDetailInfoByBlockHash.end result:{}",detailInfo);
        return detailInfo;
    }
}
