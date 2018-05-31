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
 * @file: NodeConfigController.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.BcosBrowserException;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.utils.BrowserUtils;
import org.bcos.browser.dto.TbNodeConnectionDto;
import org.bcos.browser.entity.base.BasePageRespEntity;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.entity.req.*;
import org.bcos.browser.service.TbNodeConnectionService;
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
@RequestMapping(value = "nodeConfig")
public class NodeConfigController extends BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(NodeConfigController.class);

    @Autowired
    TbNodeConnectionService tbNodeConnectionService;

    /**
     *@Description: Jump node configuration page
     */
    @RequestMapping(value = "/nodeConfig.page",method = RequestMethod.GET)
    public String toBlockPage(){
        LOGGER.info("to page:nodeConfig.....");
        return "nodeConfig";
    }


    /**
     *@Description:Paging node configuration information table
     */
    @ResponseBody
    @RequestMapping(value = "/getTbNodeConnectionByPage.json",method = RequestMethod.POST)
    public BasePageRespEntity getTbNodeConnectionByPage(@RequestBody ReqGetTbNodeConnectionByPageVO reqEntity){
        LOGGER.info("getTbNodeConnectionByPage.start reqEntity:{}",JSON.toJSONString(reqEntity));

        Map<String,Object> map = new HashedMap();
        map.put("ip",BrowserUtils.trimSpaces(reqEntity.getIpVal()));
        map.put("rpcPort",BrowserUtils.trimSpaces(reqEntity.getRpcPortVal()));
        map.put("offset",reqEntity.getStart());
        map.put("size",reqEntity.getPageSize());


        //Get the total number of records
        int total = tbNodeConnectionService.getAllNodeConnectionCount(map);

        List<TbNodeConnectionDto> list = null;
        if(total>0){
           list  = tbNodeConnectionService.getTbNodeConnectionByOffset(map);
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
     *@Description:Get node configuration information based on pkid
     */
    @ResponseBody
    @RequestMapping(value = "/getTbNodeConnectionByPkId.json",method = RequestMethod.POST)
    public BaseRspEntity getTbNodeConnectionByPkId(@Valid @RequestBody ReqGetTbNodeConnectionByPkIdVO reqEntity, BindingResult result){
        LOGGER.info("getTbNodeConnectionByPkId.start reqEntity:{}",JSON.toJSONString(reqEntity));
        //Judging whether the input parameters are wrong
        checkParamResult(result);

        TbNodeConnectionDto rowDate  = tbNodeConnectionService.getTbNodeConnectionByPkId(reqEntity.getPkId());

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);
        response.setData(rowDate);

        LOGGER.info("getTbNodeConnectionByPkId.end response:{}", JSON.toJSONString(response));
        return response;
    }

    /**
     *@Description:Add node configuration information
     */
    @ResponseBody
    @RequestMapping(value = "/addNodeConfigRow.json",method = RequestMethod.POST)
    public BaseRspEntity addNodeConfigRow(@Valid @RequestBody ReqAddNodeConfigRowVO reqEntity, BindingResult result){
        LOGGER.info("addNodeConfigRow.start reqEntity:{}",JSON.toJSONString(reqEntity));
        checkParamResult(result);

        String ipStr = reqEntity.getIp();//ip
        if(!BrowserUtils.isIp(ipStr)){
            LOGGER.error("editNodeConfigRow fail. ipStr:{}",ipStr);
            throw new BcosBrowserException(ConstantCode.SAVE_FAIL_IP_PARAM_FORMAT_ERROR);
        }

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);

        TbNodeConnectionDto addData = new TbNodeConnectionDto();
        addData.setIp(reqEntity.getIp());
        addData.setRpcPort(reqEntity.getRpcPort());

        Long row  = tbNodeConnectionService.addRow(addData);
        if(row == null || row==0){
            response = new BaseRspEntity(ConstantCode.SAVE_FAIL);
        }

        LOGGER.info("addNodeConfigRow.end response:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     *@Description:Modify node configuration information
     */
    @ResponseBody
    @RequestMapping(value = "/editNodeConfigRow.json",method = RequestMethod.POST)
    public BaseRspEntity editNodeConfigRow(@Valid @RequestBody ReqEditNodeConfigRowVO reqEntity, BindingResult result){
        LOGGER.info("editNodeConfigRow.start reqEntity:{}",JSON.toJSONString(reqEntity));

        checkParamResult(result);

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);

        String ipStr = reqEntity.getIp();//ip
        if(!BrowserUtils.isIp(ipStr)){
            LOGGER.error("editNodeConfigRow fail. ipStr:{}",ipStr);
            throw new BcosBrowserException(ConstantCode.SAVE_FAIL_IP_PARAM_FORMAT_ERROR);
        }

        Map<String,Object> map = new HashedMap();
        map.put("pkId",reqEntity.getPkId());
        map.put("ip",ipStr);
        map.put("rpcPort",reqEntity.getRpcPort());

        Integer row  = tbNodeConnectionService.updateTbNodeConnection(map);
        if(row == null || row==0){
            response = new BaseRspEntity(ConstantCode.SAVE_FAIL);
        }

        LOGGER.info("editNodeConfigRow.end response:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     *@Description:Delete node configuration information
     */
    @ResponseBody
    @RequestMapping(value = "/deleteNodeConfigRow.json",method = RequestMethod.POST)
    public BaseRspEntity deleteNodeConfigRow(@Valid @RequestBody ReqDeleteNodeConfigRowVO reqEntity, BindingResult result){
        LOGGER.info("deleteNodeConfigRow.start reqEntity:{}",JSON.toJSONString(reqEntity));

        checkParamResult(result);

        BaseRspEntity response = new BaseRspEntity(ConstantCode.SUCCESS);

        Integer row  = tbNodeConnectionService.deleteTbNodeConnection(reqEntity.getPkId());
        if(row == null || row==0){
            response = new BaseRspEntity(ConstantCode.DELETE_FAIL);
        }

        LOGGER.info("deleteNodeConfigRow.end response:{}", JSON.toJSONString(response));
        return response;
    }
}
