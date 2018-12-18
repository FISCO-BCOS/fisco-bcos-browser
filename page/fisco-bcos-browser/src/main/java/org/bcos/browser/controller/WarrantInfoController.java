package org.bcos.browser.controller;

import java.util.List;

import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.dto.TbAddWarrantEventDto;
import org.bcos.browser.entity.base.BaseRspEntity;
import org.bcos.browser.service.TbAddWarrantEventService;
import org.bcos.browser.service.TbBlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "AddWarrantEvent")
public class WarrantInfoController {
    private static Logger LOGGER =  LoggerFactory.getLogger(WarrantInfoController.class);
    @Autowired
    TbAddWarrantEventService tbAddWarrantEventService;
    

}
