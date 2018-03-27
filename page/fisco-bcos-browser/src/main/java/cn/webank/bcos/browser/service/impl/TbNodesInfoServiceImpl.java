package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.dto.TbNodesInfoDto;
import cn.webank.bcos.browser.mapper.TbNodesInfoMapper;
import cn.webank.bcos.browser.service.TbNodesInfoService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:Node Information Table, Services
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:19
 */
@Service
public class TbNodesInfoServiceImpl implements TbNodesInfoService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbNodesInfoServiceImpl.class);

    @Autowired
    TbNodesInfoMapper tbNodesInfoMapper;

    /**
     *@Description: Total number of records in the information table of the paging query node
     */
    @Override
    public int getAllNodesInfoCount() {
        LOGGER.info("getAllNodesInfoCount.start...");
        int total = tbNodesInfoMapper.getAllNodesInfoCount();
        LOGGER.info("getAllNodesInfoCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: Paging query node information table records
     */
    @Override
    public List<TbNodesInfoDto> getTbNodesInfoByOffset(Integer offset, Integer size) {
        LOGGER.info("getTbNodesInfoByOffset.start offset:{},size:{}",offset,size);
        List<TbNodesInfoDto> list = tbNodesInfoMapper.getTbNodesInfoByOffset(offset,size);
        LOGGER.info("getTbNodesInfoByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }
}
