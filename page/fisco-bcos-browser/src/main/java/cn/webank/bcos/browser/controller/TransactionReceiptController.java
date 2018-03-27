package cn.webank.bcos.browser.controller;

import cn.webank.bcos.browser.base.BaseController;
import cn.webank.bcos.browser.base.ConstantCode;
import cn.webank.bcos.browser.dto.TbTransactionReceiptDto;
import cn.webank.bcos.browser.entity.base.BaseRspEntity;
import cn.webank.bcos.browser.entity.req.ReqGetTbTransactionReceiptByPkHashVO;
import cn.webank.bcos.browser.entity.rsp.RspGetTbTransactionReceiptByPkHashVO;
import cn.webank.bcos.browser.service.TbTransactionReceiptService;
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
 * @Description:TransactionReceipt Controller
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
     *@Description:According to pkHash query transaction receipt information
     */
    @ResponseBody
    @RequestMapping(value = "/getTbTransactionReceiptByPkHash.json",method = RequestMethod.POST)
    public BaseRspEntity getTbTransactionReceiptByPkHash(@Valid @RequestBody ReqGetTbTransactionReceiptByPkHashVO reqEntity, BindingResult result){
        LOGGER.info("getTbTransactionReceiptByPkHash.start reqEntity:{}", JSON.toJSONString(reqEntity));

        checkParamResult(result);

        TbTransactionReceiptDto tbTransactionReceiptDto = tbTransactionReceiptService.getTbTransactionReceiptByPkHash(reqEntity.getPkHash());
        RspGetTbTransactionReceiptByPkHashVO rspEntity = new RspGetTbTransactionReceiptByPkHashVO();
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
