package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbNodesInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:Node Information Table，mapper
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 14:52
 */
public interface TbNodesInfoMapper {
    /*Total number of records in the information table of the paging query node*/
    int getAllNodesInfoCount();
    /*Paging query node information table records*/
    List<TbNodesInfoDto> getTbNodesInfoByOffset(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
}
