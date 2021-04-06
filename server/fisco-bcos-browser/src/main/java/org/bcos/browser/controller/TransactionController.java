package org.bcos.browser.controller;

import javax.validation.Valid;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.req.ReqGetCode;
import org.bcos.browser.entity.req.ReqTransaction;
import org.bcos.browser.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "transaction")
public class TransactionController extends BaseController {

    @Autowired
    TransactionService transactionService;

    /**
     * getTransInfoByPage.
     * 
     * @param groupId groupId
     * @param pageNumber pageNumber
     * @param pageSize pageSize
     * @param transHash transHash
     * @param blockNumber blockNumber
     * @return
     */
    @GetMapping("/transactionList/{groupId}/{pageNumber}/{pageSize}")
    public BasePageResponse getTransInfoByPage(@PathVariable("groupId") int groupId,
            @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize,
            @RequestParam(value = "transHash", required = false) String transHash,
            @RequestParam(value = "blockNumber", required = false) String blockNumber)
            throws BaseException {
        BasePageResponse response = transactionService.getTransInfoByPage(groupId, pageNumber,
                pageSize, transHash, blockNumber);
        return response;
    }

    /**
     * analyzeData.
     * 
     * @param reqTransaction info
     * @param result checkResult
     * @return
     */
    @PostMapping("/analyzeData")
    public BaseResponse analyzeData(@Valid @RequestBody ReqTransaction reqTransaction,
            BindingResult result) throws BaseException {
        checkParamResult(result);
        BaseResponse response = transactionService.analyzeData(reqTransaction);
        return response;
    }

    /**
     * getTransactionByHash.
     * 
     * @param groupId groupId
     * @param transHash transHash
     * @return
     * @throws BaseException
     */
    @GetMapping("/transactionByHash/{groupId}/{transHash}")
    public BaseResponse getTransactionByHash(@PathVariable("groupId") int groupId,
            @PathVariable("transHash") String transHash) throws BaseException {
        BaseResponse response = transactionService.getTransactionByHash(groupId, transHash);
        return response;
    }

    /**
     * getTransactionReceiptByHash.
     * 
     * @param groupId groupId
     * @param transHash transHash
     * @return
     * @throws BaseException
     */
    @GetMapping("/receiptByHash/{groupId}/{transHash}")
    public BaseResponse getReceiptByHash(@PathVariable("groupId") int groupId,
            @PathVariable("transHash") String transHash) throws BaseException {
        BaseResponse response = transactionService.getReceiptByHash(groupId, transHash);
        return response;
    }

    /**
     * getCode.
     * 
     * @param reqGetCode info
     * @param result checkResult
     * @return
     */
    @PostMapping("/code")
    public BaseResponse getCode(@Valid @RequestBody ReqGetCode reqGetCode, BindingResult result)
            throws BaseException {
        BaseResponse response = transactionService.getCode(reqGetCode);
        return response;
    }
}
