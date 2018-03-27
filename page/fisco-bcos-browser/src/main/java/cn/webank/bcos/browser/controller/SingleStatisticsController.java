package cn.webank.bcos.browser.controller;

import cn.webank.bcos.browser.base.BaseController;
import cn.webank.bcos.browser.base.ConstantCode;
import cn.webank.bcos.browser.dto.TbStatDto;
import cn.webank.bcos.browser.entity.base.BaseRspEntity;
import cn.webank.bcos.browser.entity.req.ReqListAttrDto;
import cn.webank.bcos.browser.entity.req.ReqListTbStatByObjectAttrVO;
import cn.webank.bcos.browser.entity.req.ReqListTbStatObjectVO;
import cn.webank.bcos.browser.service.SingleStatisticsService;
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

/**
 * @Description:singleStatistics Controller
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 17:11
 */
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
