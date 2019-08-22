package org.bcos.browser.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.BlockChainInfo;
import org.bcos.browser.entity.dto.TransactionByDay;
import org.bcos.browser.entity.rsp.RspGetTxnLatelyDays;
import org.bcos.browser.mapper.BlockChainInfoMapper;
import org.bcos.browser.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockChainInfoService {
    @Autowired
    BlockChainInfoMapper blockChainInfoMapper;
    @Autowired
    NodeMapper nodeMapper;

    /**
     * getBlockChainInfo.
     * 
     * @param groupId groupId
     * @return
     */
    public BaseResponse getBlockChainInfo(int groupId) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        BlockChainInfo tbBlockChainInfo = blockChainInfoMapper.getBlockChainInfo(groupId);
        response.setData(tbBlockChainInfo);
        return response;
    }

    /**
     * getTxnLatelyDays.
     * 
     * @param groupId groupId
     * @param dateTimeBegin start time
     * @param dateTimeEnd end time
     * @return
     */
    public BaseResponse getTxnLatelyDays(int groupId, String dateTimeBegin,
            String dateTimeEnd) {
        List<TransactionByDay> list =
                blockChainInfoMapper.getLastTbTxnByDay(groupId, dateTimeBegin, dateTimeEnd);
        List<RspGetTxnLatelyDays> listTxn = new ArrayList<>();
        if (list != null) {
            for (TransactionByDay tbTxnByDayDto : list) {
                Date pkDate = tbTxnByDayDto.getPkDate();
                if (pkDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(pkDate);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    String dayStr = month + "/" + day;
                    RspGetTxnLatelyDays rspEntity = new RspGetTxnLatelyDays();
                    rspEntity.setDateStr(dayStr);
                    rspEntity.setTxn(tbTxnByDayDto.getTxn());
                    listTxn.add(rspEntity);
                }
            }
        }
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        response.setData(listTxn);
        return response;
    }
}
