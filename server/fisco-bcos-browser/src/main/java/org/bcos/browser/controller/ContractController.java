package org.bcos.browser.controller;

import javax.validation.Valid;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.req.ReqContracts;
import org.bcos.browser.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "contract")
public class ContractController extends BaseController {

    @Autowired
    ContractService contractService;

    /**
     * newContract.
     * 
     * @param contracts contract info
     * @param result checkResult
     * @return
     */
    @PostMapping("/add")
    public BaseResponse addContract(@Valid @RequestBody ReqContracts contracts,
            BindingResult result) throws BaseException {
        checkParamResult(result);
        BaseResponse response = contractService.addContract(contracts);
        return response;
    }

    /**
     * getContractList.
     * 
     * @return
     */
    @GetMapping("/contractList/{groupId}/{pageNumber}/{pageSize}")
    public BasePageResponse getContractList(@PathVariable("groupId") int groupId,
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("pageSize") int pageSize) {
        BasePageResponse response = contractService.getContractList(groupId, pageNumber, pageSize);
        return response;
    }

    /**
     * updateContract.
     * 
     * @param contracts info
     * @param result checkResult
     * @return
     */
    @PutMapping("/update")
    public BaseResponse updateContract(@Valid @RequestBody ReqContracts contracts,
            BindingResult result) throws BaseException {
        checkParamResult(result);
        BaseResponse response = contractService.updateContract(contracts);
        return response;
    }

    /**
     * deleteContract.
     * 
     * @param groupId groupId
     * @param contractId contractId
     * @return
     */
    @DeleteMapping("/deleteById/{groupId}/{contractId}")
    public BaseResponse deleteContract(@PathVariable("groupId") int groupId,
            @PathVariable("contractId") int contractId) {
        BaseResponse response = contractService.deleteContract(groupId, contractId);
        return response;
    }
}
