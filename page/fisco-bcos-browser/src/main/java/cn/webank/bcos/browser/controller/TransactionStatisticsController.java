package cn.webank.bcos.browser.controller;

import cn.webank.bcos.browser.base.BaseController;
import cn.webank.bcos.browser.base.ConstantCode;
import cn.webank.bcos.browser.base.utils.BrowserUtils;
import cn.webank.bcos.browser.dto.TbStatTransactionDto;
import cn.webank.bcos.browser.entity.base.BasePageRespEntity;
import cn.webank.bcos.browser.entity.req.ReqListTbStatTransactionByPageVO;
import cn.webank.bcos.browser.service.TransactionStatisticsService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
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
import java.util.List;
import java.util.Map;

/**
 * @Description:TransactionStatistics Controller
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 17:48
 */
@Controller
@RequestMapping(value = "transactionStatistics")
public class TransactionStatisticsController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(TransactionStatisticsController.class);

    @Autowired
    TransactionStatisticsService transactionStatisticsService;

    /**
     *@Description: Jump trading process statistics page
     */
    @RequestMapping(value = "/transactionStatistics.page",method = RequestMethod.GET)
    public String toTransactionStatisticsPage(){
        LOGGER.info("to page:transactionStatistics.....");
        return "transactionStatistics";
    }

    /**
     *@Description:Paging query transaction process statistics list
     */
    @ResponseBody
    @RequestMapping(value = "/listTbStatTransactionByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity listTbStatTransactionByPage(@Valid @RequestBody ReqListTbStatTransactionByPageVO reqEntity, BindingResult result){
        LOGGER.info("listTbStatTransactionByPage start. reqEntity:{}", JSON.toJSONString(reqEntity));

        checkParamResult(result);

        Map<String,Object> map = new HashedMap();
        map.put("offset",reqEntity.getStart());
        map.put("size",reqEntity.getPageSize());
        map.put("hash", BrowserUtils.trimSpaces(reqEntity.getHash()));
        map.put("dateStr",reqEntity.getDateStr());
        map.put("timeStartStr",reqEntity.getTimeStartStr());
        map.put("timeEndStr",reqEntity.getTimeEndStr());

       int total = transactionStatisticsService.countTbStatTransaction(map);

        List<TbStatTransactionDto> list = null;
        if(total>0){
            list  = transactionStatisticsService.listTbStatTransaction(map);
        }

        BasePageRespEntity response = new BasePageRespEntity();
        response.setRetCode(ConstantCode.SUCCESS);
        response.setPageNumber(reqEntity.getPageNumber());
        response.setPageSize(reqEntity.getPageSize());
        response.setTotal(total);
        response.setList(list);

        LOGGER.info("listTbStatTransactionByPage end. response:{}", JSON.toJSONString(response));
        return response;
    }
}
