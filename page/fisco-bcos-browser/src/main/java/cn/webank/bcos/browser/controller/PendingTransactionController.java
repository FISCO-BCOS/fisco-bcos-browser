package cn.webank.bcos.browser.controller;

import cn.webank.bcos.browser.base.BaseController;
import cn.webank.bcos.browser.base.BcosBrowserException;
import cn.webank.bcos.browser.base.ConstantCode;
import cn.webank.bcos.browser.dto.TbPendingTransactionDto;
import cn.webank.bcos.browser.entity.base.BasePageReqEntity;
import cn.webank.bcos.browser.entity.base.BasePageRespEntity;
import cn.webank.bcos.browser.entity.base.BaseRspEntity;
import cn.webank.bcos.browser.entity.req.ReqGetTbPendingTransactionByPkHashVO;
import cn.webank.bcos.browser.entity.rsp.RspTbPendingTransactionInfoVO;
import cn.webank.bcos.browser.service.TbPendingTransactionService;
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
 * @Description:Controllers that are processing but not yet chained transaction data
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
     *@Description: Jump pendingTransaction page
     */
    @RequestMapping(value = "/pendingTransaction.page",method = RequestMethod.GET)
    public String toHomePage(){
        LOGGER.info("to page:pendingTransaction.....");
        return "pendingTransaction";
    }

    /**
     *@Description:Pagination query transaction data that is processed but not yet chained
     */
    @ResponseBody
    @RequestMapping(value = "/getTbPendingTransactionInfoByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity getTbPendingTransactionInfoByPage(@Valid @RequestBody BasePageReqEntity reqEntity, BindingResult result){
        LOGGER.info("getTbPendingTransactionInfoByPage.start...");

        checkParamResult(result);

        int total = tbPendingTransactionService.getAllPendingTransactionCount();

        List<RspTbPendingTransactionInfoVO> list = null;

        if(total>0){
            List<TbPendingTransactionDto> tbPendingTransactionList = tbPendingTransactionService.getTbPendingTransactionByOffset(reqEntity.getStart(),reqEntity.getPageSize());
            if(tbPendingTransactionList!=null){
                list = new ArrayList<>();
                for(TbPendingTransactionDto tbPendingTransactionDto:tbPendingTransactionList){
                    RspTbPendingTransactionInfoVO rspEntity = new RspTbPendingTransactionInfoVO();
                    rspEntity.setPkHash(tbPendingTransactionDto.getPkHash());//	TxHash
                    rspEntity.setBlockHash(tbPendingTransactionDto.getBlockHash());//		VARCHAR	32
                    rspEntity.setBlockNumber(tbPendingTransactionDto.getBlockNumber());
                    rspEntity.setTransactionIndex(tbPendingTransactionDto.getTransactionIndex());
                    rspEntity.setTransactionFrom(tbPendingTransactionDto.getTransactionFrom());
                    rspEntity.setTransactionTo(tbPendingTransactionDto.getTransactionTo());
                    rspEntity.setGas(tbPendingTransactionDto.getGas());//Used By Txn
                    rspEntity.setGasPrice(tbPendingTransactionDto.getGasPrice());
                    rspEntity.setCumulativeGas(tbPendingTransactionDto.getCumulativeGas());
                    rspEntity.setRandomId(tbPendingTransactionDto.getRandomId());
                    rspEntity.setInputText(tbPendingTransactionDto.getInputText());//Input Data
                    rspEntity.setQueueType(tbPendingTransactionDto.getQueueType());
                    list.add(rspEntity);
                }
            }
        }

        BasePageRespEntity response = new BasePageRespEntity();
        response.setRetCode(ConstantCode.SUCCESS);
        response.setPageNumber(reqEntity.getPageNumber());
        response.setPageSize(reqEntity.getPageSize());
        response.setTotal(total);
        response.setList(list);

        LOGGER.info("getTbPendingTransactionInfoByPage.end response:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     *@Description:Open the details page for transactions that are processed but not linked
     */
    @RequestMapping(value = "/getPendingTransactionDetailPage.page",method = RequestMethod.GET)
    public ModelAndView getPendingTransactionDetailPage(String pkHash){
        LOGGER.info("getPendingTransactionDetailPage.start pkHash:{}",pkHash);
        if(StringUtils.isBlank(pkHash)){
            throw new BcosBrowserException(ConstantCode.QUERY_FAIL_PK_HASH_EMPTY);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("pendingTransactionDetail");//Transaction details page
        mav.addObject("pkHash",pkHash);
        LOGGER.info("getPendingTransactionDetailPage end,response:{}", JSON.toJSONString(mav));
        return mav;
    }


    /**
     *@Description:Detailed information on transactions that are processed but not linked based on pkHash
     */
    @ResponseBody
    @RequestMapping(value = "/getTbPendingTransactionByPkHash.json",method = RequestMethod.POST)
    public BaseRspEntity getTbPendingTransactionByPkHash(@Valid @RequestBody ReqGetTbPendingTransactionByPkHashVO reqEntity, BindingResult result){
        LOGGER.info("getTbPendingTransactionByPkHash.start reqEntity:{}", JSON.toJSONString(reqEntity));
        checkParamResult(result);

        TbPendingTransactionDto tbPendingTransactionDto = tbPendingTransactionService.getTbPendingTransactionByPkHash(reqEntity.getPkHash());

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(tbPendingTransactionDto);

        LOGGER.info("getTbPendingTransactionByPkHash.end response:{}", JSON.toJSONString(response));
        return response;
    }
}
