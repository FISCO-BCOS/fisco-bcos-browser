package org.bcos.browser.service;


import org.bcos.browser.dto.TbBlockChainInfoDto;

/**
 *@Description: 区块链全局信息，服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbBlockChainInfoService {
    //获取一条区块链全局信息
    TbBlockChainInfoDto getTbBlockChainInfo();
}
