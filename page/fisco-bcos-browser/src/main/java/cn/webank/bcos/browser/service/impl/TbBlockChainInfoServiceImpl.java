package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.service.TbBlockChainInfoService;
import cn.webank.bcos.browser.dto.TbBlockChainInfoDto;
import cn.webank.bcos.browser.mapper.TbBlockChainInfoMapper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Description: Blockchain Global Information, Services
 *@Author: v_wbsqwu
 *@Date: 2017/10/13
 */
@Service
public class TbBlockChainInfoServiceImpl implements TbBlockChainInfoService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbBlockChainInfoServiceImpl.class);

    @Autowired
    TbBlockChainInfoMapper tbBlockChainInfoMapper;

    /**
     *@Description: Get a blockchain global information
     */
    @Override
    public TbBlockChainInfoDto getTbBlockChainInfo() {
        LOGGER.info("getTbBlockChainInfo.start...");
        TbBlockChainInfoDto tbBlockChainInfo = tbBlockChainInfoMapper.getTbBlockChainInfo();
        LOGGER.info("getTbBlockChainInfo.end.result:{}", JSON.toJSONString(tbBlockChainInfo));
        return tbBlockChainInfo;
    }
}
