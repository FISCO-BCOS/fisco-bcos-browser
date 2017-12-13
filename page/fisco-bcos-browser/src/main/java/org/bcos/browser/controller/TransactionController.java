package org.bcos.browser.controller;


import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.FiscoBcosBrowserException;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.utils.DateTimeUtils;
import org.bcos.browser.dto.TbTransactionDto;
import org.bcos.browser.entity.base.BasePageRespEntity;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.entity.req.ReqGetTbTransactionByPkHashEntity;
import org.bcos.browser.entity.req.ReqGetTbTransactionInfoByPageEntity;
import org.bcos.browser.entity.rsp.RspTbTransactionInfoEntity;
import org.bcos.browser.service.TbTransactionService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:交易数据的控制器
 * @Author: v_wbsqwu
 * @Date: 2017/10/16 14:34
 */
@Controller
@RequestMapping(value = "transaction")
public class TransactionController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TbTransactionService tbTransactionService;

    /**
     *@Description: 跳转交易信息页面
     */
    @RequestMapping(value = "/transaction.page",method = RequestMethod.GET)
    public ModelAndView toTransactionPage(Integer blockHeight){
        LOGGER.info("toTransactionPage.start blockNumber:{}",blockHeight);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("transaction");//块信息页
        mav.addObject("blockHeight",blockHeight==null?"":blockHeight);
        LOGGER.info("toTransactionPage.end response:{}", JSON.toJSONString(mav));
        return mav;
    }


    /**
     *@Description:分页查询交易信息表的数据
     */
    @ResponseBody
    @RequestMapping(value = "/getTbTransactionInfoByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity getTbTransactionInfoByPage(@Valid @RequestBody ReqGetTbTransactionInfoByPageEntity reqEntity, BindingResult result){
        LOGGER.info("getTbTransactionInfoByPage.start reqEntity:{}",JSON.toJSONString(reqEntity));
        //判断入参是否有误
        checkParamResult(result);

        //获取总记录数
        int total = tbTransactionService.getAllTransactionCount(reqEntity.getBlockHeight());

        List<RspTbTransactionInfoEntity> list = null;

        if(total>0){
            //获取数据列表
            List<TbTransactionDto> listTbTransactionDto = tbTransactionService.getTbTransactionByOffset(reqEntity.getBlockHeight(),reqEntity.getStart(),reqEntity.getPageSize());
            if(listTbTransactionDto!=null){
                list = new ArrayList<>();
                for(TbTransactionDto tbTransactionDto:listTbTransactionDto){
                    RspTbTransactionInfoEntity rspEntity = new RspTbTransactionInfoEntity();
                    rspEntity.setPkHash(tbTransactionDto.getPkHash());//	TxHash
                    rspEntity.setBlockHash(tbTransactionDto.getBlockHash());//		VARCHAR	32
                    rspEntity.setBlockNumber(tbTransactionDto.getBlockNumber());//块高
                    rspEntity.setBlockTimesStr(DateTimeUtils.Timestamp2String(tbTransactionDto.getBlockTimestamp(), Constants.DEFAULT_DATA_TIME_FORMAT));//时间
                    rspEntity.setBlockGasLimit(tbTransactionDto.getBlockGasLimit());//Gas Limit
                    rspEntity.setTransactionIndex(tbTransactionDto.getTransactionIndex());
                    rspEntity.setTransactionFrom(tbTransactionDto.getTransactionFrom());
                    rspEntity.setTransactionTo(tbTransactionDto.getTransactionTo());
                    rspEntity.setGas(tbTransactionDto.getGas());//Used By Txn
                    rspEntity.setGasPrice(tbTransactionDto.getGasPrice());
                    rspEntity.setCumulativeGas(tbTransactionDto.getCumulativeGas());
                    rspEntity.setRandomId(tbTransactionDto.getRandomId());
                    rspEntity.setInputText(tbTransactionDto.getInputText());//Input Data

                    list.add(rspEntity);
                }
            }
        }

        BasePageRespEntity response = new BasePageRespEntity();
        response.setRetCode(ConstantCode.SUCCESS);//成功
        response.setPageNumber(reqEntity.getPageNumber());//当前页
        response.setPageSize(reqEntity.getPageSize());
        response.setTotal(total);
        response.setList(list);

        LOGGER.info("getTbTransactionInfoByPage.end response:{}", JSON.toJSONString(response));
        return response;
    }

    /**
     *@Description:打开交易的详细信息页面
     */
    @RequestMapping(value = "/getTbTransactionDetailPage.page",method = RequestMethod.GET)
    public ModelAndView getTbTransactionDetailPage(String pkHash){
        LOGGER.info("getTbTransactionDetailPage.start pkHash:{}",pkHash);
        //pkHash为空
        if(StringUtils.isBlank(pkHash)){
            throw new FiscoBcosBrowserException(ConstantCode.QUERY_FAIL_PK_HASH_EMPTY);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("transactionDetail");//交易详细信息页
        mav.addObject("pkHash",pkHash);
        LOGGER.info("getTbTransactionDetailPage end,response:{}", JSON.toJSONString(mav));
        return mav;
    }

    /**
     *@Description:根据pkHash查询交易回执信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTbTransactionByPkHash.json",method = RequestMethod.POST)
    public BaseRspEntity getTbTransactionByPkHash(@Valid @RequestBody ReqGetTbTransactionByPkHashEntity reqEntity, BindingResult result){
        LOGGER.info("getTbTransactionByPkHash.start reqEntity:{}", JSON.toJSONString(reqEntity));
        //判断入参是否有误
        checkParamResult(result);

        //获取交易详细信息
        TbTransactionDto tbTransactionDto = tbTransactionService.getTbTransactionByPkHash(reqEntity.getPkHash());

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(tbTransactionDto);

        LOGGER.info("getTbTransactionByPkHash.end response:{}", JSON.toJSONString(response));
        return response;
    }


}
