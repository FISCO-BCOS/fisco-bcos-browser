package cn.webank.bcos.browser.mapper;


import cn.webank.bcos.browser.dto.TbBlockChainInfoDto;

/**
 *@Description: Blockchain Global Information Table,mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbBlockChainInfoMapper {
    //Get a blockchain global information
    TbBlockChainInfoDto getTbBlockChainInfo();
}
