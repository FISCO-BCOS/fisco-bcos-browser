package cn.webank.bcos.browser.service;

import cn.webank.bcos.browser.dto.TbNodeConnectionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:Node rpc link table, service
 * @Author: v_wbsqwu
 * @Date: 2018/1/11 10:06
 */
public interface TbNodeConnectionService {
    /*Get the total number of records in the node rpc link table*/
    int getAllNodeConnectionCount(Map<String,Object> map);
    /*Paging query the latest record*/
    List<TbNodeConnectionDto> getTbNodeConnectionByOffset(Map<String,Object> map);
    /*Get node configuration information based on pkid*/
    TbNodeConnectionDto getTbNodeConnectionByPkId(@Param(value = "pkId") Integer pkId);
    /*Get node configuration information based on pkid*/
    Integer deleteTbNodeConnection(@Param(value = "pkId") Integer pkId);
    /*Modify node configuration information*/
    Integer updateTbNodeConnection(Map<String,Object> map);
    /*Add record*/
    Long addRow(TbNodeConnectionDto tbNodeConnectionDto);
}
