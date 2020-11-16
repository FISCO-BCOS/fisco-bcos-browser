package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.service.BlockChainInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "home")
public class BlockChainInfoController extends BaseController {

    @Autowired
    BlockChainInfoService blockChainInfoService;

    /**
     * isConfigAuth.
     * 
     * @return
     */
    @GetMapping("/isConfigAuth")
    public BaseResponse isConfigAuth() {
        BaseResponse response = blockChainInfoService.isConfigAuth();
        return response;
    }

    /**
     * getBlockChainInfo.
     * 
     * @param groupId groupId
     * @return
     */
    @GetMapping("/blockChainInfo/{groupId}")
    public BaseResponse getBlockChainInfo(@PathVariable("groupId") int groupId)
            throws BaseException {
        BaseResponse response = blockChainInfoService.getBlockChainInfo(groupId);
        return response;
    }

    /**
     * get lately transaction counts.
     * 
     * @param groupId groupId
     * @param dateTimeBegin start time
     * @param dateTimeEnd end time
     * @return
     */
    @GetMapping("/txnLately/{groupId}/{dateTimeBegin}/{dateTimeEnd}")
    public BaseResponse getTxnLatelyDays(@PathVariable("groupId") int groupId,
            @PathVariable("dateTimeBegin") String dateTimeBegin,
            @PathVariable("dateTimeEnd") String dateTimeEnd) throws BaseException {
        BaseResponse response =
                blockChainInfoService.getTxnLatelyDays(groupId, dateTimeBegin, dateTimeEnd);
        return response;
    }
}
