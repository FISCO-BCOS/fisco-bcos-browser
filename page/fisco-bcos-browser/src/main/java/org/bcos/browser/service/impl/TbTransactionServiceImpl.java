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
 * @file: TbTransactionServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;


import org.bcos.browser.dto.TbTransactionDto;
import org.bcos.browser.mapper.TbTransactionMapper;
import org.bcos.browser.service.TbTransactionService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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