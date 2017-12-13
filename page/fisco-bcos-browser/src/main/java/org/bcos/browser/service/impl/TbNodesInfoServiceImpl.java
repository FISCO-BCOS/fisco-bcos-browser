package org.bcos.browser.service.impl;

import org.bcos.browser.dto.TbNodesInfoDto;
import org.bcos.browser.mapper.TbNodesInfoMapper;
import org.bcos.browser.service.TbNodesInfoService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:节点的信息表，服务
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:19
 */
@Service
public class TbNodesInfoServiceImpl implements TbNodesInfoService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbNodesInfoServiceImpl.class);

    @Autowired
    TbNodesInfoMapper tbNodesInfoMapper;

    /**
     *@Description: 分页查询节点的信息表的总记录数
     */
    @Override
    public int getAllNodesInfoCount() {
        LOGGER.info("getAllNodesInfoCount.start...");
        int total = tbNodesInfoMapper.getAllNodesInfoCount();
        LOGGER.info("getAllNodesInfoCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: 分页查询节点的信息表的记录
     */
    @Override
    public List<TbNodesInfoDto> getTbNodesInfoByOffset(Integer offset, Integer size) {
        LOGGER.info("getTbNodesInfoByOffset.start offset:{},size:{}",offset,size);
        List<TbNodesInfoDto> list = tbNodesInfoMapper.getTbNodesInfoByOffset(offset,size);
        LOGGER.info("getTbNodesInfoByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }
}
