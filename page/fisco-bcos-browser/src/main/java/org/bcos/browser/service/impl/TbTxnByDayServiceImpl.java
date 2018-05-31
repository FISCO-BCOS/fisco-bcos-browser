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
 * @file: TbTxnByDayServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;

import org.bcos.browser.base.Constants;
import org.bcos.browser.dto.TbTxnByDayDto;
import org.bcos.browser.entity.rsp.RspGetTxnByLastFourteenDayVO;
import org.bcos.browser.mapper.TbTxnByDayMapper;
import org.bcos.browser.service.TbTxnByDayService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TbTxnByDayServiceImpl implements TbTxnByDayService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbTxnByDayServiceImpl.class);
    @Autowired
    TbTxnByDayMapper tbTxnByDayMapper;


    /**
     *@Description:Get the last 14 days of transaction data
     */
    @Override
    public List<RspGetTxnByLastFourteenDayVO> getTxnByLastFourteenDay() {
        LOGGER.info("getTxnByLastFourteenDay.start...");
        List<TbTxnByDayDto> list = tbTxnByDayMapper.getLastTbTxnByDayByOffset(Constants.DB_QUERY_DEFAULT_OFFSET,Constants.TBTXNBYDAY_DEFAULT_SIZE);
        LOGGER.info("getTxnByLastFourteenDay. DB query result：{}",JSON.toJSONString(list));

        List<RspGetTxnByLastFourteenDayVO> listFourTeamTXN= null;
        if(list != null){
            //Sort by date from largest to largest
            Collections.sort(list);

            listFourTeamTXN = new ArrayList<>();
            for(TbTxnByDayDto tbTxnByDayDto: list){
                Date pkDate = tbTxnByDayDto.getPkDate();
                if(pkDate != null){
                    //Convert to calendar
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(pkDate);

                    int month = calendar.get(Calendar.MONTH)+1;//Month (starting from 0, actually adding 1)
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    String dayStr = month+"/"+day;

                    RspGetTxnByLastFourteenDayVO rspEntity= new RspGetTxnByLastFourteenDayVO();
                    rspEntity.setPkDateStr(dayStr);
                    rspEntity.setTransactionNumber(tbTxnByDayDto.getTransactionNumber());

                    listFourTeamTXN.add(rspEntity);
                }
            }
       }

        LOGGER.info("getTxnByLastFourteenDay.end.result:{}", JSON.toJSONString(listFourTeamTXN));
        return listFourTeamTXN;
    }
}