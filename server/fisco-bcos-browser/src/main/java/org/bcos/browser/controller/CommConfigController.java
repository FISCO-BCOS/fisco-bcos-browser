package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.service.CommConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


@RestController
@RequestMapping
public class CommConfigController extends BaseController {
    @Autowired
    CommConfigService commConfigService;

    @GetMapping("/test")
    public BaseResponse test() {
        return new BaseResponse(ConstantCode.SUCCESS);
    }

    /**
     * getWebConfig
     */
    @GetMapping("/getWebConfig")
    public BaseResponse getWebConfig() throws BaseException {
        String rootPath = System.getProperty("user.dir") + File.separator + "config";
        return commConfigService.getWebConfig(rootPath);
    }

    /**
     * 读取配置文件生成群组以及节点信息
     */
    @GetMapping("/updateGroupAndNode")
    public BaseResponse updateGroupAndNode() throws BaseException {
        String rootPath = System.getProperty("user.dir") + File.separator + "config";
        return commConfigService.updateGroupAndNode(rootPath);
    }
}
