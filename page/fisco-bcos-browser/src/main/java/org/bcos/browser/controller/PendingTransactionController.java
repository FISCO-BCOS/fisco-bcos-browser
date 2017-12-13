package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.FiscoBcosBrowserException;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.dto.TbPendingTransactionDto;
import org.bcos.browser.entity.base.BasePageReqEntity;
import org.bcos.browser.entity.base.BasePageRespEntity;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.entity.req.ReqGetTbPendingTransactionByPkHashEntity;
import org.bcos.browser.entity.rsp.RspTbPendingTransactionInfoEntity;
import org.bcos.browser.service.TbPendingTransactionService;
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
 * @Description:正在处理但还未上链的交易数据的控制器
 * @Author: v_wbsqwu
 * @Date: 2017/10/17 21:13
 */
@Controller
@RequestMapping(value = "pendingTransaction")
public class PendingTransactionController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(PendingTransactionController.class);

    @Autowired
    TbPendingTransactionService tbPendingTransactionService;


    /**
     *@Description: 跳转首页
     */
    @RequestMapping(value = "/pendingTransaction.page",method = RequestMethod.GET)
    public String toHomePage(){
        LOGGER.info("to page:pendingTransaction.....");
        return "pendingTransaction";
    }

    /**
     *@Description:分页查询正在处理但还未上链的交易数据
     */
    @ResponseBody
    @RequestMapping(value = "/getTbPendingTransactionInfoByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity getTbPendingTransactionInfoByPage(@Valid @RequestBody BasePageReqEntity reqEntity, BindingResult result){
        LOGGER.info("getTbPendingTransactionInfoByPage.start...");
        //判断入参是否有误
        checkParamResult(result);

        //获取总记录数
        int total = tbPendingTransactionService.getAllPendingTransactionCount();

        List<RspTbPendingTransactionInfoEntity> list = null;

        if(total>0){
            //获取数据列表
            List<TbPendingTransactionDto> tbPendingTransactionList = tbPendingTransactionService.getTbPendingTransactionByOffset(reqEntity.getStart(),reqEntity.getPageSize());
            if(tbPendingTransactionList!=null){
                list = new ArrayList<>();
                for(TbPendingTransactionDto tbPendingTransactionDto:tbPendingTransactionList){
                    RspTbPendingTransactionInfoEntity rspEntity = new RspTbPendingTransactionInfoEntity();
                    rspEntity.setPkHash(tbPendingTransactionDto.getPkHash());//	TxHash
                    rspEntity.setBlockHash(tbPendingTransactionDto.getBlockHash());//		VARCHAR	32
                    rspEntity.setBlockNumber(tbPendingTransactionDto.getBlockNumber());//块高
                    rspEntity.setTransactionIndex(tbPendingTransactionDto.getTransactionIndex());
                    rspEntity.setTransactionFrom(tbPendingTransactionDto.getTransactionFrom());
                    rspEntity.setTransactionTo(tbPendingTransactionDto.getTransactionTo());
                    rspEntity.setGas(tbPendingTransactionDto.getGas());//Used By Txn
                    rspEntity.setGasPrice(tbPendingTransactionDto.getGasPrice());
                    rspEntity.setCumulativeGas(tbPendingTransactionDto.getCumulativeGas());
                    rspEntity.setRandomId(tbPendingTransactionDto.getRandomId());
                    rspEntity.setInputText(tbPendingTransactionDto.getInputText());//Input Data
                    rspEntity.setQueueType(tbPendingTransactionDto.getQueueType());//队列类型
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

        LOGGER.info("getTbPendingTransactionInfoByPage.end response:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     *@Description:打开正在处理但未上联的交易的详细信息页面
     */
    @RequestMapping(value = "/getPendingTransactionDetailPage.page",method = RequestMethod.GET)
    public ModelAndView getPendingTransactionDetailPage(String pkHash){
        LOGGER.info("getPendingTransactionDetailPage.start pkHash:{}",pkHash);
        //pkHash为空
        if(StringUtils.isBlank(pkHash)){
            throw new FiscoBcosBrowserException(ConstantCode.QUERY_FAIL_PK_HASH_EMPTY);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("pendingTransactionDetail");//交易详细信息页
        mav.addObject("pkHash",pkHash);
        LOGGER.info("getPendingTransactionDetailPage end,response:{}", JSON.toJSONString(mav));
        return mav;
    }


    /**
     *@Description:根据pkHash查询正在处理但未上联的交易的详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTbPendingTransactionByPkHash.json",method = RequestMethod.POST)
    public BaseRspEntity getTbPendingTransactionByPkHash(@Valid @RequestBody ReqGetTbPendingTransactionByPkHashEntity reqEntity, BindingResult result){
        LOGGER.info("getTbPendingTransactionByPkHash.start reqEntity:{}", JSON.toJSONString(reqEntity));
        //判断入参是否有误
        checkParamResult(result);

        //获取交易详细信息
        TbPendingTransactionDto tbPendingTransactionDto = tbPendingTransactionService.getTbPendingTransactionByPkHash(reqEntity.getPkHash());

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(tbPendingTransactionDto);

        LOGGER.info("getTbPendingTransactionByPkHash.end response:{}", JSON.toJSONString(response));
        return response;
    }
}
