package org.bcos.browser.service;

import org.bcos.browser.dto.TbNodesInfoDto;

import java.util.List;

/**
 * @Description:节点的信息表，服务
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:19
 */
public interface TbNodesInfoService {
    /*分页查询节点的信息表的总记录数*/
    int getAllNodesInfoCount();
    /*分页查询节点的信息表的记录*/
    List<TbNodesInfoDto> getTbNodesInfoByOffset(Integer offset, Integer size);
}
