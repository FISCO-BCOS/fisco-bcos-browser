package org.bcos.browser.mapper;


import org.bcos.browser.dto.TbBlockChainInfoDto;

/**
 *@Description: 区块链全局信息表,mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbBlockChainInfoMapper {
    //获取一条区块链全局信息
    TbBlockChainInfoDto getTbBlockChainInfo();
}
