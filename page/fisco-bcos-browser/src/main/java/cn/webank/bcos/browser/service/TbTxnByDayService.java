package cn.webank.bcos.browser.service;

import cn.webank.bcos.browser.entity.rsp.RspGetTxnByLastFourteenDayVO;

import java.util.List;

/**
 *@Description: Daily volume record table, service
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbTxnByDayService {
    /*Get the last 14 days of transaction data*/
    List<RspGetTxnByLastFourteenDayVO> getTxnByLastFourteenDay();
}
