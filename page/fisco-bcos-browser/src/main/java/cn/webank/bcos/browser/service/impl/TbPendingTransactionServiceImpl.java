package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.dto.TbPendingTransactionDto;
import cn.webank.bcos.browser.dto.TbTransactionDto;
import cn.webank.bcos.browser.mapper.TbPendingTransactionMapper;
import cn.webank.bcos.browser.service.TbPendingTransactionService;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Description: Dealing with but not yet linked transaction tables, services
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
@Service
public class TbPendingTransactionServiceImpl implements TbPendingTransactionService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbPendingTransactionServiceImpl.class);

    @Autowired
    TbPendingTransactionMapper tbPendingTransactionMapper;

    /**
     *@Description: Get the total number of records that are processing but not yet linked to the transaction table
     */
    @Override
    public int getAllPendingTransactionCount() {
        LOGGER.info("getAllPendingTransactionCount.start...");
        int total = tbPendingTransactionMapper.getAllPendingTransactionCount();
        LOGGER.info("getAllPendingTransactionCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: Paging query the latest record
     */
    @Override
    public List<TbPendingTransactionDto> getTbPendingTransactionByOffset(Integer offset, Integer size) {
        LOGGER.info("getTbPendingTransactionByOffset.start offset:{},size:{}",offset,size);
        List<TbPendingTransactionDto> list = tbPendingTransactionMapper.getTbPendingTransactionByOffset(offset,size);
        LOGGER.info("getTbPendingTransactionByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: According to pkHash query
     */
    @Override
    public TbPendingTransactionDto getTbPendingTransactionByPkHash(@Param("pkHash") String pkHash) {
        LOGGER.info("getTbPendingTransactionByPkHash.start pkHash:{}",pkHash);
        TbPendingTransactionDto tbPendingTransactionDto = tbPendingTransactionMapper.getTbPendingTransactionByPkHash(pkHash);
        LOGGER.info("getTbPendingTransactionByPkHash.end result:{}", JSON.toJSONString(tbPendingTransactionDto));
        return tbPendingTransactionDto;
    }


}
