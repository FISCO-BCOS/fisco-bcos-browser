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
 * @file: SingleStatisticsController.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.dto.TbStatDto;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.entity.req.ReqListAttrDto;
import org.bcos.browser.entity.req.ReqListTbStatByObjectAttrVO;
import org.bcos.browser.entity.req.ReqListTbStatObjectVO;
import org.bcos.browser.service.SingleStatisticsService;
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
import java.util.List;

@Controller
@RequestMapping(value = "singleStatistics")
public class SingleStatisticsController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(SingleStatisticsController.class);

    @Autowired
    SingleStatisticsService singleStatisticsService;

    /**
     *@Description: Jump to single point statistics page
     */
    @RequestMapping(value = "/singleStatistics.page",method = RequestMethod.GET)
    public String toSingleStatisticsPage(){
        LOGGER.info("to page:singleStatistics.....");
        return "singleStatistics";
    }



    /**
     *@Description:Query Object list
     */
    @ResponseBody
    @RequestMapping(value = "/listTbStatObject.json",method = RequestMethod.POST)
    public BaseRspEntity listTbStatObject(@RequestBody ReqListTbStatObjectVO reqVo){
        LOGGER.info("listTbStatObject.start. reqVo:{}", JSON.toJSONString(reqVo));

        List<String> list = singleStatisticsService.listObject(reqVo.getDateStr());

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(list);

        LOGGER.info("listTbStatObject.end response:{}", JSON.toJSONString(response));
        return response;
    }



    /**
     *@Description:list attr
     */
    @ResponseBody
    @RequestMapping(value = "/listAttr.json",method = RequestMethod.POST)
    public BaseRspEntity listAttr(@RequestBody ReqListAttrDto reqVo){
        LOGGER.info("listAttr.start. reqVo:{}", JSON.toJSONString(reqVo));

        List<TbStatDto> list = singleStatisticsService.listAttr(reqVo.getDateStr());

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(list);

        LOGGER.info("listAttr.end response:{}", JSON.toJSONString(response));
        return response;
    }



    /**
     *@Description:Query a single point statistics list
     */
    @ResponseBody
    @RequestMapping(value = "/ListTbStat.json",method = RequestMethod.POST)
    public BaseRspEntity ListTbStat(@Valid @RequestBody ReqListTbStatByObjectAttrVO reqVo, BindingResult result){
        LOGGER.info("ListTbStat.start ...");

        checkParamResult(result);

        List<List<TbStatDto>> list = singleStatisticsService.ListTbStat(reqVo);

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(list);

        LOGGER.info("ListTbStat.end response:{}", JSON.toJSONString(response));
        return response;
    }
}
