/*
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: TbBlockServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;

import org.bcos.browser.dto.TbBlockDto;
import org.bcos.browser.mapper.TbBlockMapper;
import org.bcos.browser.service.TbBlockService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TbBlockServiceImpl implements TbBlockService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbBlockServiceImpl.class);

    @Autowired
    TbBlockMapper tbBlockMapper;

    /**
     *@Description: Query the total number of block table records
     */
    @Override
    public int getAllBlockCount(Map<String,Object> map) {
        LOGGER.info("getAllBlockCount start. map:{}",JSON.toJSONString(map));
        int total = tbBlockMapper.getAllBlockCount(map);
        LOGGER.info("getAllBlockCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: Paging query block table records
     */
    @Override
    public List<TbBlockDto> getBlockInfoByOffset(Map<String,Object> map) {
        LOGGER.info("getBlockInfoByByOffset start. map:{}",JSON.toJSONString(map));
        List<TbBlockDto> list = tbBlockMapper.getBlockInfoByOffset(map);
        LOGGER.info("getBlockInfoByByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: Query block details based on block height
     */
    @Override
    public String getBlockDetailInfoByBlockHash(String blockHash) {
        LOGGER.info("getBlockDetailInfoByBlockHash.start blockHash:{}",blockHash);
        TbBlockDto tbBlockDto = tbBlockMapper.getBlockDetailInfoByBlockHash(blockHash);
        LOGGER.info("getBlockDetailInfoByBlockHash blockHash:{},result:{}",blockHash,JSON.toJSONString(tbBlockDto));

        if (tbBlockDto == null){
            LOGGER.warn("query fail:tbBlockDto is null !");
            return null;
        }

        String detailInfo = tbBlockDto.getDetailInfo();
        LOGGER.info("getBlockDetailInfoByBlockHash.end result:{}",detailInfo);
        return detailInfo;
    }
}