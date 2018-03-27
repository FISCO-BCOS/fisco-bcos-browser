package cn.webank.bcos.browser.controller;


import cn.webank.bcos.browser.dto.TbNodesInfoDto;
import cn.webank.bcos.browser.entity.base.BasePageReqEntity;
import cn.webank.bcos.browser.entity.base.BasePageRespEntity;
import cn.webank.bcos.browser.entity.rsp.RspGetTbNodesInfoByPageVO;
import cn.webank.bcos.browser.entity.rsp.RspGetTxnByLastFourteenDayVO;
import cn.webank.bcos.browser.service.TbBlockChainInfoService;
import cn.webank.bcos.browser.base.BaseController;
import cn.webank.bcos.browser.base.ConstantCode;
import cn.webank.bcos.browser.dto.TbBlockChainInfoDto;
import cn.webank.bcos.browser.entity.base.BaseRspEntity;
import cn.webank.bcos.browser.service.TbNodesInfoService;
import cn.webank.bcos.browser.service.TbTxnByDayService;
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
import java.util.ArrayList;
import java.util.List;

/**
 *@Description: Home Controller
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 14:53
 */
@Controller
@RequestMapping(value = "home")
public class HomeController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(HomeController.class);

    @Autowired
    TbBlockChainInfoService tbBlockChainInfoService;
    @Autowired
    TbTxnByDayService tbTxnByDayService;
    @Autowired
    TbNodesInfoService tbNodesInfoService;

    /**
     *@Description:Default page
     */
    @RequestMapping(value = "/default.page",method = RequestMethod.GET)
    public String toDefaultPage(){
        LOGGER.info("to page:redirect home.....");
        return "redirect:home/home.page";
    }

    /**
     *@Description: Jump Home
     */
    @RequestMapping(value = "/home.page",method = RequestMethod.GET)
    public String toHomePage(){
        LOGGER.info("to page:home.....");
        return "home";
    }

    /**
     *@Description:Get a blockchain global information
     */
    @ResponseBody
    @RequestMapping(value = "/getTbBlockChainInfo.json",method = RequestMethod.POST)
    public BaseRspEntity getTbBlockChainInfo(){
        LOGGER.info("getTbBlockChainInfo.start...");
        TbBlockChainInfoDto tbBlockChainInfo = tbBlockChainInfoService.getTbBlockChainInfo();

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(tbBlockChainInfo);

        LOGGER.info("getTbBlockChainInfo.end response:{}", JSON.toJSONString(response));
        return response;
    }

    /**
     *@Description:Get the last 14 days of transaction data
     */
    @ResponseBody
    @RequestMapping(value = "/getTxnByLastFourteenDay.json",method = RequestMethod.POST)
    public BaseRspEntity getTxnByLastFourteenDay(){
        LOGGER.info("getTxnByLastFourteenDay.start...");
        List<RspGetTxnByLastFourteenDayVO> list = tbTxnByDayService.getTxnByLastFourteenDay();
        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(list);
        LOGGER.info("getTxnByLastFourteenDay.end response:{}", JSON.toJSONString(response));
        return response;
    }



    /**
     *@Description:分页查询节点的信息表的数据
     */
    @ResponseBody
    @RequestMapping(value = "/getTbNodesInfoByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity getTbNodesInfoByPage(@Valid @RequestBody BasePageReqEntity reqEntity, BindingResult result){
        LOGGER.info("getTbNodesInfoByPage.start reqEntity:{}",JSON.toJSONString(reqEntity));
        checkParamResult(result);

        //Get the total number of records
        int total = tbNodesInfoService.getAllNodesInfoCount();

        List<RspGetTbNodesInfoByPageVO> list = null;
        if(total>0){
            List<TbNodesInfoDto> ListTbNodesInfoDto = tbNodesInfoService.getTbNodesInfoByOffset(reqEntity.getStart(),reqEntity.getPageSize());
            if(ListTbNodesInfoDto!=null){
                list = new ArrayList<>();
                for(TbNodesInfoDto tbNodesInfoDto:ListTbNodesInfoDto){
                    RspGetTbNodesInfoByPageVO rspEntity = new RspGetTbNodesInfoByPageVO();
                    rspEntity.setPkId(tbNodesInfoDto.getPkId());
                    rspEntity.setAddr(tbNodesInfoDto.getAddr());
                    rspEntity.setBlockNumber(tbNodesInfoDto.getBlockNumber());
                    rspEntity.setActive(tbNodesInfoDto.getActive());
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

        LOGGER.info("getTbNodesInfoByPage.end response:{}", JSON.toJSONString(response));
        return response;
    }
}
