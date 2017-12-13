package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.FiscoBcosBrowserException;
import org.bcos.browser.base.utils.DateTimeUtils;
import org.bcos.browser.dto.TbBlockDto;
import org.bcos.browser.entity.base.BasePageReqEntity;
import org.bcos.browser.entity.base.BasePageRespEntity;
import org.bcos.browser.entity.rsp.RspTbBlockInfoEntity;
import org.bcos.browser.service.TbBlockService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
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
 * @Description:
 * @Author: v_wbsqwu
 * @Date: 2017/10/15 17:48
 */
@Controller
@RequestMapping(value = "block")
public class BlockController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(BlockController.class);

    @Autowired
    TbBlockService tbBlockService;

    /**
     *@Description: 跳转块信息页面
     */
    @RequestMapping(value = "/block.page",method = RequestMethod.GET)
    public String toBlockPage(){
        LOGGER.info("to page:block.....");
        return "block";
    }


    /**
     *@Description:分页查询区块的表
     */
    @ResponseBody
    @RequestMapping(value = "/getTbBlockInfoByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity getTbBlockInfoByPage(@Valid @RequestBody BasePageReqEntity reqEntity, BindingResult result){
        LOGGER.info("getTbBlockInfoByPage.start reqEntity:{}",JSON.toJSONString(reqEntity));
        //判断入参是否有误
        checkParamResult(result);

        //获取总记录数
        int total = tbBlockService.getAllBlockCount();

        List<RspTbBlockInfoEntity> list = null;
        if(total>0){
            //获取数据列表
            List<TbBlockDto> listTbBlockDto  = tbBlockService.getBlockInfoByOffset(reqEntity.getStart(),reqEntity.getPageSize());
            if(listTbBlockDto!=null){
                list = new ArrayList<>();
                for(TbBlockDto tbBlockDto: listTbBlockDto){
                    RspTbBlockInfoEntity rspEntity = new RspTbBlockInfoEntity();
                    rspEntity.setNumber(tbBlockDto.getNumber());//块高
                    rspEntity.setPkHash(tbBlockDto.getPkHash());//hash值
                    rspEntity.setParentHash(tbBlockDto.getParentHash());//上级hash
                    rspEntity.setMiner(tbBlockDto.getMiner());//矿工
                    rspEntity.setGenIndex(tbBlockDto.getGenIndex());
                    rspEntity.setSize(tbBlockDto.getSize());
                    rspEntity.setGasLimit(tbBlockDto.getGasLimit());
                    rspEntity.setGasUsed(tbBlockDto.getGasUsed());
                    rspEntity.setAvgGasPrice(tbBlockDto.getAvgGasPrice());//块内交易的平均gasPrice
                    rspEntity.setDateTimeStr(DateTimeUtils.Timestamp2String(tbBlockDto.getTimestamp(), Constants.DEFAULT_DATA_TIME_FORMAT));//时间
                    rspEntity.setTransactionNumber(tbBlockDto.getTransactionNumber());//块包含的交易数
                    rspEntity.setExtraData(tbBlockDto.getExtraData());
                    rspEntity.setDetailInfo(tbBlockDto.getDetailInfo());//rpc查询结果的所有数据
                    rspEntity.setAvgTime(tbBlockDto.getAvgTime());

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

        LOGGER.info("getTbBlockInfoByPage.end response:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     *@Description:打开块的详细信息页面
     */
    @RequestMapping(value = "/getTbBlockDetailPage.page",method = RequestMethod.GET)
    public ModelAndView getTbBlockDetailPage(String blockHash){
        LOGGER.info("getTbBlockDetailPage.start blockHash:{}",blockHash);

        //blockHash为空
        if(StringUtils.isBlank(blockHash)){
            throw new FiscoBcosBrowserException(ConstantCode.QUERY_FAIL_BLOCK_HASH_EMPTY);
        }

        //获取块高详细信息
        String blockDetailInfo = tbBlockService.getBlockDetailInfoByBlockHash(blockHash);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("blockDetail");//块高详细页
        mav.addObject("blockDetailInfo", blockDetailInfo);
        mav.addObject("blockHash",blockHash);
        LOGGER.info("getTbBlockDetailPage end,response:{}", JSON.toJSONString(mav));
        return mav;
    }
}
