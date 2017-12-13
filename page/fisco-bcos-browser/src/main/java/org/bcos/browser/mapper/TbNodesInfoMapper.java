package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbNodesInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:节点的信息表，mapper
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 14:52
 */
public interface TbNodesInfoMapper {
    /*分页查询节点的信息表的总记录数*/
    int getAllNodesInfoCount();
    /*分页查询节点的信息表的记录*/
    List<TbNodesInfoDto> getTbNodesInfoByOffset(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
}
