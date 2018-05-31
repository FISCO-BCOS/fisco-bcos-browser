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
 * @file: TbBlockChainInfoServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;

import org.bcos.browser.service.TbBlockChainInfoService;
import org.bcos.browser.dto.TbBlockChainInfoDto;
import org.bcos.browser.mapper.TbBlockChainInfoMapper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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