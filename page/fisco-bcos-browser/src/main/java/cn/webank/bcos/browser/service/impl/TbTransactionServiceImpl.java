package cn.webank.bcos.browser.service.impl;


import cn.webank.bcos.browser.dto.TbTransactionDto;
import cn.webank.bcos.browser.entity.req.ReqGetTbTransactionInfoByPageVO;
import cn.webank.bcos.browser.mapper.TbTransactionMapper;
import cn.webank.bcos.browser.service.TbTransactionService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *@Description: Table of each transaction, service
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
@Service
public class TbTransactionServiceImpl implements TbTransactionService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbTransactionServiceImpl.class);

    @Autowired
    TbTransactionMapper tbTransactionMapper;

    /**
     *@Description: Get the total number of records in the transaction information table
     */
    @Override
    public int getAllTransactionCount(Map<String,Object> map) {
        LOGGER.info("getAllTransactionCount start. map:{}",JSON.toJSONString(map));
        int total = tbTransactionMapper.getAllTransactionCount(map);
        LOGGER.info("getAllTransactionCount end result:{}", total);
        return total;
    }

    /**
     *@Description: Paging query the latest record
     */
    @Override
    public List<TbTransactionDto> getTbTransactionByOffset(Map<String,Object> map) {
        LOGGER.info("getTbTransactionByOffset start. map:{}",JSON.toJSONString(map));
        List<TbTransactionDto> list = tbTransactionMapper.getTbTransactionByOffset(map);
        LOGGER.info("getTbTransactionByOffset end. result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: According to pkHash query transaction table records
     */
    @Override
    public TbTransactionDto getTbTransactionByPkHash(String pkHash) {
        LOGGER.info("getTbTransactionByPkHash start. pkHash:{}",pkHash);
        TbTransactionDto tbTransactionDto = tbTransactionMapper.getTbTransactionByPkHash(pkHash);
        LOGGER.info("getTbTransactionByPkHash end. result:{}", JSON.toJSONString(tbTransactionDto));
        return tbTransactionDto;
    }
}
