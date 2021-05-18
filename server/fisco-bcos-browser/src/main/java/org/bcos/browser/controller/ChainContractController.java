package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.service.ChainContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "chainContract")
public class ChainContractController extends BaseController {

    @Autowired
    ChainContractService chainContractService;

    /**
     * getContractList.
     *
     * @return
     */
    @GetMapping("/list/{groupId}/{pageNumber}/{pageSize}")
    public BasePageResponse getChainContractList(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("pageSize") int pageSize,
            @PathVariable("groupId") int groupId,
            @RequestParam(value = "contractAddress", required = false) String contractAddress
    ) {
        BasePageResponse response = chainContractService.getChainContractList(groupId, contractAddress, pageNumber, pageSize);
        return response;
    }

    /**
     * getContractList.
     *
     * @return
     */
    @GetMapping("/transactionList/{groupId}/{contractAddress}/{pageNumber}/{pageSize}")
    public BasePageResponse getChainContractList(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("contractAddress") String contractAddress,
            @PathVariable("pageSize") int pageSize,
            @PathVariable("groupId") int groupId,
            @RequestParam(value = "userParam", required = false) String userParam) throws BaseException {
        BasePageResponse response = chainContractService.getTransContractList(groupId, contractAddress, userParam, pageNumber, pageSize);
        return response;
    }

}

