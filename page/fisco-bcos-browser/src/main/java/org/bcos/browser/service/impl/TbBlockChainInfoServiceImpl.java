package org.bcos.browser.service.impl;

import org.bcos.browser.service.TbBlockChainInfoService;
import org.bcos.browser.dto.TbBlockChainInfoDto;
import org.bcos.browser.mapper.TbBlockChainInfoMapper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Description: 区块链全局信息，服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13
 */
@Service
public class TbBlockChainInfoServiceImpl implements TbBlockChainInfoService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbBlockChainInfoServiceImpl.class);

    @Autowired
    TbBlockChainInfoMapper tbBlockChainInfoMapper;

    /**
     *@Description: 获取一条区块链全局信息
     */
    @Override
    public TbBlockChainInfoDto getTbBlockChainInfo() {
        LOGGER.info("getTbBlockChainInfo.start...");
        TbBlockChainInfoDto tbBlockChainInfo = tbBlockChainInfoMapper.getTbBlockChainInfo();
        LOGGER.info("getTbBlockChainInfo.end.result:{}", JSON.toJSONString(tbBlockChainInfo));
        return tbBlockChainInfo;
    }
}
