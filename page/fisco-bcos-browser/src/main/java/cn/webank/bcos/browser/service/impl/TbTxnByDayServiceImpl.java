package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.base.Constants;
import cn.webank.bcos.browser.dto.TbTxnByDayDto;
import cn.webank.bcos.browser.entity.rsp.RspGetTxnByLastFourteenDayVO;
import cn.webank.bcos.browser.mapper.TbTxnByDayMapper;
import cn.webank.bcos.browser.service.TbTxnByDayService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *@Description: Daily volume record table, service
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
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
