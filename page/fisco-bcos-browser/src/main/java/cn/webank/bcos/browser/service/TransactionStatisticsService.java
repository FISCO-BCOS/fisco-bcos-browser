package cn.webank.bcos.browser.service;

import cn.webank.bcos.browser.dto.TbStatTransactionDto;

import java.util.List;
import java.util.Map;

/**
 * @Description:Trading Process Statistics Table, Services
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 21:37
 */
public interface TransactionStatisticsService {
    //Query transaction process statistics list total records
    int countTbStatTransaction(Map<String,Object> map);
    //Paging query transaction process statistics list
    List<TbStatTransactionDto> listTbStatTransaction(Map<String,Object> map);
}
