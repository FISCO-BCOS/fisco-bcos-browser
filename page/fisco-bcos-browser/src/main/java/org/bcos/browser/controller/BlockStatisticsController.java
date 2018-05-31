package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.utils.BrowserUtils;
import org.bcos.browser.dto.TbStatBlockDto;
import org.bcos.browser.entity.base.BasePageRespEntity;
import org.bcos.browser.entity.req.ReqListTbStatBlockByPageVO;
import org.bcos.browser.service.BlockStatisticsService;
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
 * @file: BlockStatisticsController.java
 * @author: v_wbsqwu
 * @date: 2018
 */
@Controller
@RequestMapping(value = "blockStatistics")
public class BlockStatisticsController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(BlockStatisticsController.class);

    @Autowired
    BlockStatisticsService blockStatisticsService;

    /**
     *@Description: Jump out of the block flow statistics page
     */
    @RequestMapping(value = "/blockStatistics.page",method = RequestMethod.GET)
    public String toBlockStatisticsPage(){
        LOGGER.info("to page:blockStatistics.....");
        return "blockStatistics";
    }



    /**
     *@Description:Paging query block process statistics list
     */
    @ResponseBody
    @RequestMapping(value = "/listTbStatBlockByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity listTbStatBlockByPage(@Valid @RequestBody ReqListTbStatBlockByPageVO reqEntity, BindingResult result){
        LOGGER.info("listTbStatBlockByPage start. reqEntity:{}", JSON.toJSONString(reqEntity));
        //Judging whether the input parameters are wrong
        checkParamResult(result);

        Map<String,Object> map = new HashedMap();
        map.put("offset",reqEntity.getStart());
        map.put("size",reqEntity.getPageSize());
        map.put("hash", BrowserUtils.trimSpaces(reqEntity.getHash()));
        map.put("dateStr",reqEntity.getDateStr());
        map.put("timeStartStr",reqEntity.getTimeStartStr());
        map.put("timeEndStr",reqEntity.getTimeEndStr());


        //Get the total number of records
        int total = blockStatisticsService.countTbStatBlock(map);

        List<TbStatBlockDto> list = null;
        if(total>0){
            //Get data list
            list  = blockStatisticsService.listTbStatBlock(map);
        }

        BasePageRespEntity response = new BasePageRespEntity();
        response.setRetCode(ConstantCode.SUCCESS);
        response.setPageNumber(reqEntity.getPageNumber());
        response.setPageSize(reqEntity.getPageSize());
        response.setTotal(total);
        response.setList(list);

        LOGGER.info("listTbStatBlockByPage end. response:{}", JSON.toJSONString(response));
        return response;
    }
}