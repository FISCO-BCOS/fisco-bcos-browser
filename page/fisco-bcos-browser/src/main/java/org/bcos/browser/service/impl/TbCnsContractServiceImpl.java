package org.bcos.browser.service.impl;

import java.util.List;

import org.bcos.browser.dto.TbCnsContractDto;
import org.bcos.browser.mapper.TbCnsContractMapper;
import org.bcos.browser.mapper.TbMarketAuctionSuccessEventMapper;
import org.bcos.browser.service.TbCnsContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbCnsContractServiceImpl implements TbCnsContractService{
    private static Logger LOGGER =  LoggerFactory.getLogger(BlockStatisticsServiceImpl.class);
    @Autowired
    TbCnsContractMapper tbCnsContractMapper;
    @Override
    public TbCnsContractDto getContractAInfoByAddress(String contractAddress) {
        LOGGER.info("getAllMarketAuctionSuccessEvent start.");
        TbCnsContractDto ret = tbCnsContractMapper.getContractAInfoByAddress(contractAddress);
        LOGGER.info("getAllMarketAuctionSuccessEvent end result {}",ret.toString());
        return ret;
    }
}
