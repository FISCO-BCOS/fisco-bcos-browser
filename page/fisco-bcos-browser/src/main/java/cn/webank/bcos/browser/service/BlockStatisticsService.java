package cn.webank.bcos.browser.service;

import cn.webank.bcos.browser.dto.TbStatBlockDto;

import java.util.List;
import java.util.Map;

/**
 * @Description:Block flow statistics, services
 * @Author: v_wbsqwu
 * @Date: 2017/11/23 10:22
 */
public interface BlockStatisticsService {
    //Query the total number of records in the block flow statistics list
    int countTbStatBlock(Map<String,Object> map);
    //Paging query block process statistics list
    List<TbStatBlockDto> listTbStatBlock(Map<String,Object> map);
}
