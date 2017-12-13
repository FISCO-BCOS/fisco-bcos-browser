package org.bcos.browser.service;

import org.bcos.browser.entity.rsp.RspGetTxnByLastFourteenDayEntity;

import java.util.List;

/**
 *@Description: 每日交易量记录表,服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbTxnByDayService {
    /*获取最近14天的交易数据*/
    List<RspGetTxnByLastFourteenDayEntity> getTxnByLastFourteenDay();
}
