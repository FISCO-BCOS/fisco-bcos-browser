package org.bcos.browser.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.dto.TbAddWarrantEventDto;
import org.bcos.browser.dto.TbMarketAuctionSuccessEventDto;
import org.bcos.browser.mapper.TbAddWarrantEventMapper;
import org.bcos.browser.mapper.TbMarketAuctionSuccessEventMapper;
import org.bcos.browser.service.TbAddWarrantEventService;
import org.bcos.browser.service.TbMarketAuctionSuccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TbMarketAuctionSuccessServiceImpl implements TbMarketAuctionSuccessService{
    private static Logger LOGGER =  LoggerFactory.getLogger(BlockStatisticsServiceImpl.class);
    @Autowired
    TbMarketAuctionSuccessEventMapper tbMarketAuctionSuccessEventMapper;

    /**
     *@Description: Get the total number of records in the transaction information table
     */
    @Override
    public List<TbMarketAuctionSuccessEventDto> getAllMarketAuctionSuccessEvent() {
        LOGGER.info("getAllMarketAuctionSuccessEvent start.");
        List<TbMarketAuctionSuccessEventDto> ret = tbMarketAuctionSuccessEventMapper.getAllMarketAuctionSuccessEvent();
        LOGGER.info("getAllMarketAuctionSuccessEvent end result size:{}", ret.size());
        return ret;
    }

    @Override
    public List<TbMarketAuctionSuccessEventDto> getMarketAuctionSuccessEventByID(@Param("warrantID")String warrantID) {
        LOGGER.info("getAddWarrantEventByID start.");
        List<TbMarketAuctionSuccessEventDto> ret = tbMarketAuctionSuccessEventMapper.getMarketAuctionSuccessEventByID(warrantID);
        LOGGER.info("getAddWarrantEventByID end result warrantID:{}", warrantID);
        return ret;
    }
}
