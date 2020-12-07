package org.bcos.browser.controller;

import java.io.IOException;
import org.bcos.browser.auth.ConfigAuth;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Contract;
import org.bcos.browser.entity.req.ReqContracts;
import org.bcos.browser.entity.req.ReqFunction;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    @ConfigAuth
    @PostMapping("/add")
    public BaseResponse addContract(@RequestBody ReqContracts contracts,
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
    @ConfigAuth
    @PostMapping("/addBatch")
    public BaseResponse addBatchContracts(@RequestParam MultipartFile zipFile) 
    		throws IOException, BaseException {
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
    @ConfigAuth
    @PutMapping("/update")
    public BaseResponse updateContract(@RequestBody ReqContracts contracts,
            BindingResult result) throws BaseException {
        checkParamResult(result);
        BaseResponse response = contractService.updateContract(contracts);
        return response;
    }
    
    /**
     * addFunction.
     * 
     * @param functions info
     * @return
     * @throws BaseException
     */
    @ConfigAuth
    @PostMapping("/addFunction")
    public BaseResponse addFunction(@RequestBody ReqFunction functions) {
        BaseResponse response = contractService.addFunction(functions);
        return response;
    }

    /**
     * getFunction.
     * 
     * @param methodId methodId
     * @return
     */
    @GetMapping("/function/{methodId}")
    public BaseResponse getFunction(@PathVariable("methodId") String methodId) {
        BaseResponse response = contractService.getFunction(methodId);
        return response;
    }
    
    /**
     * getContractAbi.
     * 
     * @param input info
     * @return
     */
    @PostMapping("/abi")
    public BaseResponse getContractAbi(@RequestBody String input) {
        BaseResponse response = contractService.getContractAbi(input);
        return response;
    }
    
    /**
     * deleteContract.
     *
     * @param  contractNOs contractNOs
     * @return
     */
    @ConfigAuth
    @DeleteMapping("/deleteById")
    public BaseResponse deleteContract(@RequestParam("contractId") String contractNOs) {
        BaseResponse response = contractService.deleteContract(contractNOs);
        return response;
    }
}
