package org.bcos.browser.controller;

import javax.validation.Valid;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Contract;
import org.bcos.browser.entity.req.ReqContracts;
import org.bcos.browser.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
     * newContracts.
     *
     * @param zipFile
     * @return
     */
    @PostMapping("/addBatch")
    public BaseResponse addBatchContracts(@RequestParam MultipartFile zipFile
    ) throws IOException, BaseException {
        Contract contracts = new Contract();
        BaseResponse response = contractService.addBatchContracts(zipFile,contracts);
        return response;
    }


    /**
     * getContractList.
     * 
     * @return
     */
    @GetMapping("/contractList/{pageNumber}/{pageSize}")
    public BasePageResponse getContractList(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("pageSize") int pageSize) {
        BasePageResponse response = contractService.getContractList(pageNumber, pageSize);
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
     * @param  contractNOs contractNOs
     * @return
     */
    @DeleteMapping("/deleteById")
    public BaseResponse deleteContract(@RequestParam("contractId") String contractNOs) {
        BaseResponse response = contractService.deleteContract(contractNOs);
        return response;
    }
}
