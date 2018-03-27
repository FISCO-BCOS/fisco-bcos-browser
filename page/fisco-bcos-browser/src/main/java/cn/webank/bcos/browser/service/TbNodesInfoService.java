package cn.webank.bcos.browser.service;

import cn.webank.bcos.browser.dto.TbNodesInfoDto;

import java.util.List;

/**
 * @Description:Node Information Table, Services
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:19
 */
public interface TbNodesInfoService {
    /*Total number of records in the information table of the paging query node*/
    int getAllNodesInfoCount();
    /*Paging query node information table records*/
    List<TbNodesInfoDto> getTbNodesInfoByOffset(Integer offset, Integer size);
}
