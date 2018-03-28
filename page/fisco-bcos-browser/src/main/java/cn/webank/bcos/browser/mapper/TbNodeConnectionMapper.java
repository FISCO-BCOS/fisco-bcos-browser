package cn.webank.bcos.browser.mapper;


import cn.webank.bcos.browser.dto.TbNodeConnectionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@Description: Node rpc link table,mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbNodeConnectionMapper {
    /*Get the total number of records in the node rpc link table*/
    int getAllNodeConnectionCount(Map<String,Object> map);
    /*Paging query the latest record*/
    List<TbNodeConnectionDto> getTbNodeConnectionByOffset(Map<String,Object> map);
    /*Get node configuration information based on pkid*/
    TbNodeConnectionDto getTbNodeConnectionByPkId(@Param(value = "pkId") Integer pkId);
    /*Delete node configuration information*/
    Integer deleteTbNodeConnection(@Param(value = "pkId") Integer pkId);
    /*Modify node configuration information*/
    Integer updateTbNodeConnection(Map<String,Object> map);
    /*Add record*/
    Long addRow(TbNodeConnectionDto tbNodeConnectionDto);
}
