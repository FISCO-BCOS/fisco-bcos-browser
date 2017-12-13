package org.bcos.browser.service.impl;

import org.bcos.browser.dto.TbPendingTransactionDto;
import org.bcos.browser.mapper.TbPendingTransactionMapper;
import org.bcos.browser.service.TbPendingTransactionService;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Description: 正在处理但还未上链的交易表,服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
@Service
public class TbPendingTransactionServiceImpl implements TbPendingTransactionService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbPendingTransactionServiceImpl.class);

    @Autowired
    TbPendingTransactionMapper tbPendingTransactionMapper;

    /**
     *@Description: 获取正在处理但还未上链的交易表的总记录数
     */
    @Override
    public int getAllPendingTransactionCount() {
        LOGGER.info("getAllPendingTransactionCount.start...");
        int total = tbPendingTransactionMapper.getAllPendingTransactionCount();
        LOGGER.info("getAllPendingTransactionCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: 分页查询最新的记录
     */
    @Override
    public List<TbPendingTransactionDto> getTbPendingTransactionByOffset(Integer offset, Integer size) {
        LOGGER.info("getTbPendingTransactionByOffset.start offset:{},size:{}",offset,size);
        List<TbPendingTransactionDto> list = tbPendingTransactionMapper.getTbPendingTransactionByOffset(offset,size);
        LOGGER.info("getTbPendingTransactionByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: 根据pkHash查询
     */
    @Override
    public TbPendingTransactionDto getTbPendingTransactionByPkHash(@Param("pkHash") String pkHash) {
        LOGGER.info("getTbPendingTransactionByPkHash.start pkHash:{}",pkHash);
        TbPendingTransactionDto tbPendingTransactionDto = tbPendingTransactionMapper.getTbPendingTransactionByPkHash(pkHash);
        LOGGER.info("getTbPendingTransactionByPkHash.end result:{}", JSON.toJSONString(tbPendingTransactionDto));
        return tbPendingTransactionDto;
    }


}
