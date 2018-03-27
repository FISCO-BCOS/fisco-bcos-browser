package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.dto.TbNodeConnectionDto;
import cn.webank.bcos.browser.mapper.TbNodeConnectionMapper;
import cn.webank.bcos.browser.service.TbNodeConnectionService;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:Node rpc link table, service
 * @Author: v_wbsqwu
 * @Date: 2018/1/11 10:09
 */

@Service
public class TbNodeConnectionServiceImpl implements TbNodeConnectionService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbNodeConnectionServiceImpl.class);

    @Autowired
    TbNodeConnectionMapper tbNodeConnectionMapper;

    /**
     *@Description: Get the total number of records in the node rpc link table
     */
    @Override
    public int getAllNodeConnectionCount(Map<String, Object> map) {
        LOGGER.info("getAllNodeConnectionCount start. map:{}", JSON.toJSONString(map));
        int total = tbNodeConnectionMapper.getAllNodeConnectionCount(map);
        LOGGER.info("getAllNodeConnectionCount end result:{}", total);
        return total;
    }


    /**
     *@Description:Paging query the latest record
     */
    @Override
    public List<TbNodeConnectionDto> getTbNodeConnectionByOffset(Map<String, Object> map) {
        LOGGER.info("getTbNodeConnectionByOffset start. map:{}",JSON.toJSONString(map));
        List<TbNodeConnectionDto> list = tbNodeConnectionMapper.getTbNodeConnectionByOffset(map);
        LOGGER.info("getTbNodeConnectionByOffset end. result:{}", JSON.toJSONString(list));
        return list;
    }


    /**
     *@Description: Get node configuration information based on pkid
     */
    @Override
    public TbNodeConnectionDto getTbNodeConnectionByPkId(@Param(value = "pkId") Integer pkId) {
        LOGGER.info("getTbNodeConnectionByPkId start. pkId:{}",pkId);
        TbNodeConnectionDto tbNodeConnectionDto = tbNodeConnectionMapper.getTbNodeConnectionByPkId(pkId);
        LOGGER.info("getTbNodeConnectionByPkId end. result:{}", JSON.toJSONString(tbNodeConnectionDto));
        return tbNodeConnectionDto;
    }


    /**
     *@Description: Delete node configuration information
     */
    @Override
    public Integer deleteTbNodeConnection(@Param(value = "pkId") Integer pkId) {
        LOGGER.info("deleteTbNodeConnection start. pkId:{}",pkId);
        Integer result = tbNodeConnectionMapper.deleteTbNodeConnection(pkId);
        LOGGER.info("deleteTbNodeConnection end. result:{}", result);
        return result;
    }


    /**
     *@Description: Modify node configuration information
     */
    @Override
    public Integer updateTbNodeConnection(Map<String, Object> map) {
        LOGGER.info("updateTbNodeConnection start. map:{}",JSON.toJSONString(map));
        Integer result = tbNodeConnectionMapper.updateTbNodeConnection(map);
        LOGGER.info("updateTbNodeConnection end. result:{}", result);
        return result;
    }

    /**
     *@Description: Add record
     */
    @Override
    public Long addRow(TbNodeConnectionDto tbNodeConnectionDto) {
        LOGGER.info("addNodeConfigRow start. tbNodeConnectionDto:{}",JSON.toJSONString(tbNodeConnectionDto));
        Long result = tbNodeConnectionMapper.addRow(tbNodeConnectionDto);
        LOGGER.info("addNodeConfigRow end. result:{}", result);
        return result;
    }
}