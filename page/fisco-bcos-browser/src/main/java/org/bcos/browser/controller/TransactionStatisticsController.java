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
 * @file: TransactionStatisticsController.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.utils.BrowserUtils;
import org.bcos.browser.dto.TbStatTransactionDto;
import org.bcos.browser.entity.base.BasePageRespEntity;
import org.bcos.browser.entity.req.ReqListTbStatTransactionByPageVO;
import org.bcos.browser.service.TransactionStatisticsService;
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
