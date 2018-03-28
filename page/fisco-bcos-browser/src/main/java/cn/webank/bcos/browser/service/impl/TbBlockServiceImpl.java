package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.dto.TbBlockDto;
import cn.webank.bcos.browser.mapper.TbBlockMapper;
import cn.webank.bcos.browser.service.TbBlockService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *@Description: Each block, service
 *@Author: v_wbsqwu
 *@Date: 2017/10/13
 */
@Service
public class TbBlockServiceImpl implements TbBlockService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbBlockServiceImpl.class);

    @Autowired
    TbBlockMapper tbBlockMapper;

    /**
     *@Description: Query the total number of block table records
     */
    @Override
    public int getAllBlockCount(Map<String,Object> map) {
        LOGGER.info("getAllBlockCount start. map:{}",JSON.toJSONString(map));
        int total = tbBlockMapper.getAllBlockCount(map);
        LOGGER.info("getAllBlockCount.end result:{}", total);
        return total;
    }

    /**
     *@Description: Paging query block table records
     */
    @Override
    public List<TbBlockDto> getBlockInfoByOffset(Map<String,Object> map) {
        LOGGER.info("getBlockInfoByByOffset start. map:{}",JSON.toJSONString(map));
        List<TbBlockDto> list = tbBlockMapper.getBlockInfoByOffset(map);
        LOGGER.info("getBlockInfoByByOffset.end result:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     *@Description: Query block details based on block height
     */
    @Override
    public String getBlockDetailInfoByBlockHash(String blockHash) {
        LOGGER.info("getBlockDetailInfoByBlockHash.start blockHash:{}",blockHash);
        TbBlockDto tbBlockDto = tbBlockMapper.getBlockDetailInfoByBlockHash(blockHash);
        LOGGER.info("getBlockDetailInfoByBlockHash blockHash:{},result:{}",blockHash,JSON.toJSONString(tbBlockDto));

        if (tbBlockDto == null){
            LOGGER.warn("query fail:tbBlockDto is null !");
            return null;
        }

        String detailInfo = tbBlockDto.getDetailInfo();
        LOGGER.info("getBlockDetailInfoByBlockHash.end result:{}",detailInfo);
        return detailInfo;
    }
}
