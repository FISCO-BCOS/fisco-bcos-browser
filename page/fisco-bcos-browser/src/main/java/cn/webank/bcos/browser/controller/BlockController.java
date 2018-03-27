package cn.webank.bcos.browser.controller;

import cn.webank.bcos.browser.base.*;
import cn.webank.bcos.browser.base.utils.BrowserUtils;
import cn.webank.bcos.browser.base.utils.DateTimeUtils;
import cn.webank.bcos.browser.dto.TbBlockDto;
import cn.webank.bcos.browser.entity.base.BasePageReqEntity;
import cn.webank.bcos.browser.entity.base.BasePageRespEntity;
import cn.webank.bcos.browser.entity.req.ReqGetTbBlockInfoByPageVO;
import cn.webank.bcos.browser.entity.rsp.RspTbBlockInfoVO;
import cn.webank.bcos.browser.service.TbBlockService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
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
import java.util.Map;

/**
 * @Description:Block Controller
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
     *@Description: Jump block information page
     */
    @RequestMapping(value = "/block.page",method = RequestMethod.GET)
    public String toBlockPage(){
        LOGGER.info("to page:block.....");
        return "block";
    }


    /**
     *@Description:Paging query block table
     */
    @ResponseBody
    @RequestMapping(value = "/getTbBlockInfoByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity getTbBlockInfoByPage(@Valid @RequestBody ReqGetTbBlockInfoByPageVO reqEntity, BindingResult result){
        LOGGER.info("getTbBlockInfoByPage.start reqEntity:{}",JSON.toJSONString(reqEntity));
        checkParamResult(result);

        Map<String,Object> map = new HashedMap();
        map.put("hash", BrowserUtils.trimSpaces(reqEntity.getHash()));
        map.put("dateTime1",reqEntity.getDateTime1());
        map.put("dateTime2",reqEntity.getDateTime2());
        map.put("offset",reqEntity.getStart());
        map.put("size",reqEntity.getPageSize());


        //Get the total number of records
        int total = tbBlockService.getAllBlockCount(map);

        List<RspTbBlockInfoVO> list = null;
        if(total>0){
            //Get data list
            List<TbBlockDto> listTbBlockDto  = tbBlockService.getBlockInfoByOffset(map);
            if(listTbBlockDto!=null){
                list = new ArrayList<>();
                for(TbBlockDto tbBlockDto: listTbBlockDto){
                    RspTbBlockInfoVO rspEntity = new RspTbBlockInfoVO();
                    rspEntity.setNumber(tbBlockDto.getNumber());
                    rspEntity.setPkHash(tbBlockDto.getPkHash());
                    rspEntity.setParentHash(tbBlockDto.getParentHash());
                    rspEntity.setMiner(tbBlockDto.getMiner());
                    rspEntity.setGenIndex(tbBlockDto.getGenIndex());
                    rspEntity.setSize(tbBlockDto.getSize());
                    rspEntity.setGasLimit(tbBlockDto.getGasLimit());
                    rspEntity.setGasUsed(tbBlockDto.getGasUsed());
                    rspEntity.setAvgGasPrice(tbBlockDto.getAvgGasPrice());
                    rspEntity.setDateTimeStr(DateTimeUtils.Timestamp2String(tbBlockDto.getTimestamp(), Constants.DEFAULT_DATA_TIME_FORMAT));
                    rspEntity.setTransactionNumber(tbBlockDto.getTransactionNumber());
                    rspEntity.setExtraData(tbBlockDto.getExtraData());
                    rspEntity.setDetailInfo(tbBlockDto.getDetailInfo());
                    rspEntity.setAvgTime(tbBlockDto.getAvgTime());

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

        LOGGER.info("getTbBlockInfoByPage.end response:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     *@Description:Open the block details page
     */
    @RequestMapping(value = "/getTbBlockDetailPage.page",method = RequestMethod.GET)
    public ModelAndView getTbBlockDetailPage(String blockHash){
        LOGGER.info("getTbBlockDetailPage.start blockHash:{}",blockHash);

        if(StringUtils.isBlank(blockHash)){
            throw new BcosBrowserException(ConstantCode.QUERY_FAIL_BLOCK_HASH_EMPTY);
        }

        //Get block high detail
        String blockDetailInfo = tbBlockService.getBlockDetailInfoByBlockHash(blockHash);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("blockDetail");
        mav.addObject("blockDetailInfo", blockDetailInfo);
        mav.addObject("blockHash",blockHash);
        LOGGER.info("getTbBlockDetailPage end,response:{}", JSON.toJSONString(mav));
        return mav;
    }
}
