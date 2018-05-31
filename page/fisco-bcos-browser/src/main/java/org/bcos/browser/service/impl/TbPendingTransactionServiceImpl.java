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
 * @file: TbPendingTransactionServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;

import org.bcos.browser.dto.TbPendingTransactionDto;
import org.bcos.browser.mapper.TbPendingTransactionMapper;
import org.bcos.browser.service.TbPendingTransactionService;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbPendingTransactionServiceImpl implements TbPendingTransactionService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbPendingTransactionServiceImpl.class);

    @Autowired
    TbPendingTransactionMapper tbPendingTransactionMapper;

    /**
     *@Description: Get the total number of records that are processing but not yet linked to the transaction table
     */
    @Override
    public int getAllPendingTransactionCount() {
        LOGGER.info("getAllPendingTransactionCount.start...");
        int total = tbPendingTransactionMapper.getAllPendingTransactionCount();
        LOGGER.info("getAllPendingTransactionCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: Paging query the latest record
     */
    @Override
    public List<TbPendingTransactionDto> getTbPendingTransactionByOffset(Integer offset, Integer size) {
        LOGGER.info("getTbPendingTransactionByOffset.start offset:{},size:{}",offset,size);
        List<TbPendingTransactionDto> list = tbPendingTransactionMapper.getTbPendingTransactionByOffset(offset,size);
        LOGGER.info("getTbPendingTransactionByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: According to pkHash query
     */
    @Override
    public TbPendingTransactionDto getTbPendingTransactionByPkHash(@Param("pkHash") String pkHash) {
        LOGGER.info("getTbPendingTransactionByPkHash.start pkHash:{}",pkHash);
        TbPendingTransactionDto tbPendingTransactionDto = tbPendingTransactionMapper.getTbPendingTransactionByPkHash(pkHash);
        LOGGER.info("getTbPendingTransactionByPkHash.end result:{}", JSON.toJSONString(tbPendingTransactionDto));
        return tbPendingTransactionDto;
    }


}