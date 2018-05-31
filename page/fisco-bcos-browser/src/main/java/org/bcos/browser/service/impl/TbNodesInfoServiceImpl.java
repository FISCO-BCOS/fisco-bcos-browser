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
 * @file: TbNodesInfoServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;

import org.bcos.browser.dto.TbNodesInfoDto;
import org.bcos.browser.mapper.TbNodesInfoMapper;
import org.bcos.browser.service.TbNodesInfoService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbNodesInfoServiceImpl implements TbNodesInfoService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbNodesInfoServiceImpl.class);

    @Autowired
    TbNodesInfoMapper tbNodesInfoMapper;

    /**
     *@Description: Total number of records in the information table of the paging query node
     */
    @Override
    public int getAllNodesInfoCount() {
        LOGGER.info("getAllNodesInfoCount.start...");
        int total = tbNodesInfoMapper.getAllNodesInfoCount();
        LOGGER.info("getAllNodesInfoCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: Paging query node information table records
     */
    @Override
    public List<TbNodesInfoDto> getTbNodesInfoByOffset(Integer offset, Integer size) {
        LOGGER.info("getTbNodesInfoByOffset.start offset:{},size:{}",offset,size);
        List<TbNodesInfoDto> list = tbNodesInfoMapper.getTbNodesInfoByOffset(offset,size);
        LOGGER.info("getTbNodesInfoByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }
}