package org.bcos.browser.service.impl;

import org.bcos.browser.base.Constants;
import org.bcos.browser.dto.TbTxnByDayDto;
import org.bcos.browser.entity.rsp.RspGetTxnByLastFourteenDayEntity;
import org.bcos.browser.mapper.TbTxnByDayMapper;
import org.bcos.browser.service.TbTxnByDayService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *@Description: 每日交易量记录表，服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
@Service
public class TbTxnByDayServiceImpl implements TbTxnByDayService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbTxnByDayServiceImpl.class);
    @Autowired
    TbTxnByDayMapper tbTxnByDayMapper;


    /**
     *@Description:获取最近14天的交易数据
     */
    @Override
    public List<RspGetTxnByLastFourteenDayEntity> getTxnByLastFourteenDay() {
        LOGGER.info("getTxnByLastFourteenDay.start...");
        List<TbTxnByDayDto> list = tbTxnByDayMapper.getLastTbTxnByDayByOffset(Constants.DB_QUERY_DEFAULT_OFFSET,Constants.TBTXNBYDAY_DEFAULT_SIZE);
        LOGGER.info("getTxnByLastFourteenDay. DB query result：{}",JSON.toJSONString(list));

        List<RspGetTxnByLastFourteenDayEntity> listFourTeamTXN= null;
        if(list != null){
            //按日期从小到大排序
            Collections.sort(list);

            listFourTeamTXN = new ArrayList<>();
            for(TbTxnByDayDto tbTxnByDayDto: list){
                Date pkDate = tbTxnByDayDto.getPkDate();
                if(pkDate != null){
                    //转换成日历
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(pkDate);

                    int month = calendar.get(Calendar.MONTH)+1;//月（从0开始，实际要加1）
                    int day = calendar.get(Calendar.DAY_OF_MONTH);//日

                    String dayStr = month+"/"+day;

                    RspGetTxnByLastFourteenDayEntity  rspEntity= new RspGetTxnByLastFourteenDayEntity();
                    rspEntity.setPkDateStr(dayStr);//日期
                    rspEntity.setTransactionNumber(tbTxnByDayDto.getTransactionNumber());//交易数

                    listFourTeamTXN.add(rspEntity);
                }
            }
       }

        LOGGER.info("getTxnByLastFourteenDay.end.result:{}", JSON.toJSONString(listFourTeamTXN));
        return listFourTeamTXN;
    }
}
