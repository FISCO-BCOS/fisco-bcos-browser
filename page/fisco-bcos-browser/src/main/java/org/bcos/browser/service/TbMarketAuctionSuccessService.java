package org.bcos.browser.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.dto.TbAddWarrantEventDto;
import org.bcos.browser.dto.TbMarketAuctionSuccessEventDto;

public interface TbMarketAuctionSuccessService {
    public List<TbMarketAuctionSuccessEventDto> getAllMarketAuctionSuccessEvent();
    public List<TbMarketAuctionSuccessEventDto> getMarketAuctionSuccessEventByID(@Param("warrantID")String warrantID);
}
