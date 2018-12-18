package org.bcos.browser.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.dto.TbMarketAuctionSuccessEventDto;

public interface TbMarketAuctionSuccessEventMapper {
    public List<TbMarketAuctionSuccessEventDto> getAllMarketAuctionSuccessEvent();
    public List<TbMarketAuctionSuccessEventDto> getMarketAuctionSuccessEventByID(@Param(value = "warrantID")String warrantID);

}
