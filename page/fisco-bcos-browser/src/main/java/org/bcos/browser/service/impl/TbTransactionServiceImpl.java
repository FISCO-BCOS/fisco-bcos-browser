package org.bcos.browser.service.impl;


import org.bcos.browser.dto.TbTransactionDto;
import org.bcos.browser.mapper.TbTransactionMapper;
import org.bcos.browser.service.TbTransactionService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Description: 每个交易的表,服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
@Service
public class TbTransactionServiceImpl implements TbTransactionService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbTransactionServiceImpl.class);

    @Autowired
    TbTransactionMapper tbTransactionMapper;

    /**
     *@Description: 获取交易信息表的总记录数
     */
    @Override
    public int getAllTransactionCount(Integer blockHeight) {
        LOGGER.info("getAllTransactionCount.start blockHeight:{}",blockHeight);
        int total = tbTransactionMapper.getAllTransactionCount(blockHeight);
        LOGGER.info("getAllTransactionCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: 分页查询最新的记录
     */
    @Override
    public List<TbTransactionDto> getTbTransactionByOffset(Integer blockHeight, Integer offset, Integer size) {
        LOGGER.info("getTbTransactionByOffset.start offset:{},size:{}",offset,size);
        List<TbTransactionDto> list = tbTransactionMapper.getTbTransactionByOffset(blockHeight,offset,size);
        LOGGER.info("getTbTransactionByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: 根据pkHash查询交易表的记录
     */
    @Override
    public TbTransactionDto getTbTransactionByPkHash(String pkHash) {
        LOGGER.info("getTbTransactionByPkHash.start pkHash:{}",pkHash);
        TbTransactionDto tbTransactionDto = tbTransactionMapper.getTbTransactionByPkHash(pkHash);
        LOGGER.info("getTbTransactionByPkHash.end result:{}", JSON.toJSONString(tbTransactionDto));
        return tbTransactionDto;
    }
}
