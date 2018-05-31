/*
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: TransactionReceiptController.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.dto.TbTransactionReceiptDto;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.entity.req.ReqGetTbTransactionReceiptByPkHashVO;
import org.bcos.browser.entity.rsp.RspGetTbTransactionReceiptByPkHashVO;
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