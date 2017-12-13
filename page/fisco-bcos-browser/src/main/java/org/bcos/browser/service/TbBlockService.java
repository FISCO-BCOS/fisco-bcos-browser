package org.bcos.browser.service;


import org.bcos.browser.dto.TbBlockDto;

import java.util.List;

/**
 *@Description: 每个区块的，服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbBlockService {
    /*查询区块表记录总数*/
    int getAllBlockCount();
    /*分页查询区块表的记录*/
    List<TbBlockDto> getBlockInfoByOffset(Integer offset, Integer size);
    /*根据块的Hash查询块详细信息*/
    String getBlockDetailInfoByBlockHash(String blockHash);
}
