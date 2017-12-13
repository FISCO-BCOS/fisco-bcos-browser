package org.bcos.browser.controller;


import org.bcos.browser.dto.TbNodesInfoDto;
import org.bcos.browser.entity.base.BasePageReqEntity;
import org.bcos.browser.entity.base.BasePageRespEntity;
import org.bcos.browser.entity.rsp.RspGetTbNodesInfoByPageEntity;
import org.bcos.browser.entity.rsp.RspGetTxnByLastFourteenDayEntity;
import org.bcos.browser.service.TbBlockChainInfoService;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.dto.TbBlockChainInfoDto;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.service.TbNodesInfoService;
import org.bcos.browser.service.TbTxnByDayService;
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
 *@Description: 首页的控制器
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
     *@Description:默认页
     */
    @RequestMapping(value = "/default.page",method = RequestMethod.GET)
    public String toDefaultPage(){
        LOGGER.info("to page:redirect home.....");
        return "redirect:home/home.page";
    }

    /**
     *@Description: 跳转首页
     */
    @RequestMapping(value = "/home.page",method = RequestMethod.GET)
    public String toHomePage(){
        LOGGER.info("to page:home.....");
        return "home";
    }

    /**
     *@Description:获取一条区块链全局信息
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
     *@Description:获取最近14天的交易数据
     */
    @ResponseBody
    @RequestMapping(value = "/getTxnByLastFourteenDay.json",method = RequestMethod.POST)
    public BaseRspEntity getTxnByLastFourteenDay(){
        LOGGER.info("getTxnByLastFourteenDay.start...");
        List<RspGetTxnByLastFourteenDayEntity> list = tbTxnByDayService.getTxnByLastFourteenDay();
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
        //判断入参是否有误
        checkParamResult(result);

        //获取总记录数
        int total = tbNodesInfoService.getAllNodesInfoCount();

        List<RspGetTbNodesInfoByPageEntity> list = null;
        if(total>0){
            //获取数据列表
            List<TbNodesInfoDto> ListTbNodesInfoDto = tbNodesInfoService.getTbNodesInfoByOffset(reqEntity.getStart(),reqEntity.getPageSize());
            if(ListTbNodesInfoDto!=null){
                list = new ArrayList<>();
                for(TbNodesInfoDto tbNodesInfoDto:ListTbNodesInfoDto){
                    RspGetTbNodesInfoByPageEntity rspEntity = new RspGetTbNodesInfoByPageEntity();
                    rspEntity.setPkId(tbNodesInfoDto.getPkId());
                    rspEntity.setAddr(tbNodesInfoDto.getAddr());
                    rspEntity.setBlockNumber(tbNodesInfoDto.getBlockNumber());

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

        LOGGER.info("getTbNodesInfoByPage.end response:{}", JSON.toJSONString(response));
        return response;
    }
}
