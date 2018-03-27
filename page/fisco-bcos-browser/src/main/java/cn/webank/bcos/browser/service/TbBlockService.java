package cn.webank.bcos.browser.service;


import cn.webank.bcos.browser.dto.TbBlockDto;

import java.util.List;
import java.util.Map;

/**
 *@Description: Each block, service
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbBlockService {
    /*Query the total number of block table records*/
    int getAllBlockCount(Map<String,Object> map);
    /*Paging query block table records*/
    List<TbBlockDto> getBlockInfoByOffset(Map<String,Object> map);
    /*Hash query block details based on the block*/
    String getBlockDetailInfoByBlockHash(String blockHash);
}
