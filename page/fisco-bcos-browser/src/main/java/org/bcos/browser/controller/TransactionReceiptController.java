package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.dto.TbTransactionReceiptDto;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.entity.req.ReqGetTbTransactionReceiptByPkHashEntity;
import org.bcos.browser.entity.rsp.RspGetTbTransactionReceiptByPkHashEntity;
import org.bcos.browser.service.TbTransactionReceiptService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @Description:交易回执信息表，控制器
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:44
 */
@Controller
@RequestMapping(value = "transactionReceipt")
public class TransactionReceiptController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(TransactionReceiptController.class);

    @Autowired
    TbTransactionReceiptService tbTransactionReceiptService;


    /**
     *@Description:根据pkHash查询交易回执信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTbTransactionReceiptByPkHash.json",method = RequestMethod.POST)
    public BaseRspEntity getTbTransactionReceiptByPkHash(@Valid @RequestBody ReqGetTbTransactionReceiptByPkHashEntity reqEntity, BindingResult result){
        LOGGER.info("getTbTransactionReceiptByPkHash.start reqEntity:{}", JSON.toJSONString(reqEntity));
        //判断入参是否有误
        checkParamResult(result);

        //根据哈希查询回执
        TbTransactionReceiptDto tbTransactionReceiptDto = tbTransactionReceiptService.getTbTransactionReceiptByPkHash(reqEntity.getPkHash());
        RspGetTbTransactionReceiptByPkHashEntity rspEntity = new RspGetTbTransactionReceiptByPkHashEntity();
        rspEntity.setPkHash(tbTransactionReceiptDto.getPkHash());
        rspEntity.setBlockHash(tbTransactionReceiptDto.getBlockHash());
        rspEntity.setBlockNumber(tbTransactionReceiptDto.getBlockNumber());
        rspEntity.setContractAddress(tbTransactionReceiptDto.getContractAddress());
        rspEntity.setTransactionIndex(tbTransactionReceiptDto.getTransactionIndex());
        rspEntity.setGasUsed(tbTransactionReceiptDto.getGasUsed());
        rspEntity.setCumulativeGasUsed(tbTransactionReceiptDto.getCumulativeGasUsed());
        rspEntity.setLogs(tbTransactionReceiptDto.getLogs());

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(rspEntity);

        LOGGER.info("getTbTransactionReceiptByPkHash.end response:{}", JSON.toJSONString(response));
        return response;
    }
}
