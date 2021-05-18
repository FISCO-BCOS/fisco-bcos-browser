package org.bcos.browser.controller;

import lombok.extern.log4j.Log4j2;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.dto.Transaction;
import org.bcos.browser.entity.dto.UserQueryParam;
import org.bcos.browser.entity.rsp.RspGetTransaction;
import org.bcos.browser.entity.rsp.RsqChainUser;
import org.bcos.browser.service.ChainUserService;
import org.bcos.browser.service.UserService;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.DateTimeUtils;
import org.bcos.browser.util.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * ChainUserController.
 */
@Log4j2
@RestController
@RequestMapping("chainUser")
public class ChainUserController extends BaseController {

    @Autowired
    private ChainUserService chainUserService;
    @Autowired
    private UserService userService;

    /**
     * qurey chainUser info list.
     */
    @GetMapping(value = "/list/{groupId}/{pageNumber}/{pageSize}")
    public BasePageResponse chainUserList(@PathVariable("groupId") Integer groupId,
                                     @PathVariable("pageNumber") Integer pageNumber,
                                     @PathVariable("pageSize") Integer pageSize,
                                     @RequestParam(value = "address", required = false) String userParam)
            throws BaseException {
        BasePageResponse pagesponse = new BasePageResponse(ConstantCode.SUCCESS);
        Instant startTime = Instant.now();
        log.info("start userList startTime:{} groupId:{} pageNumber:{} pageSize:{} userParam:{}",
                startTime.toEpochMilli(), groupId, pageNumber, pageSize, userParam);
        Integer start = Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);
        UserQueryParam param = new UserQueryParam();
        param.setGroupId(groupId);
        param.setStart(start);
        param.setPageSize(pageSize);
        param.setUserParam(userParam);
        Integer count = chainUserService.countOfChainUser(param);
        List<RsqChainUser> listOfUser = chainUserService.queryChainUserList(param);
        pagesponse.setData(listOfUser);
        pagesponse.setTotalCount(count);
        log.info("end userList useTime:{} result:{}",
                Duration.between(startTime, Instant.now()).toMillis(),
                JsonTools.toJSONString(pagesponse));
        return pagesponse;
    }

    /**
     * qurey userTrans info list.
     */
    @GetMapping(value = "/transactionList/{groupId}/{address}/{pageNumber}/{pageSize}")
    public BasePageResponse userTransList(@PathVariable("groupId") int groupId,
                                          @PathVariable("address") String address,
                                          @PathVariable("pageNumber") Integer pageNumber,
                                          @PathVariable("pageSize") Integer pageSize,
                                          @RequestParam(value = "userParam", required = false) String userParam)
            throws BaseException {
        Instant startTime = Instant.now();
        log.info("start userList startTime:{} groupId:{} address:{} pageNumber:{} pageSize:{} userParam:{}",
                startTime.toEpochMilli(), groupId,address, pageNumber, pageSize, userParam);
        BasePageResponse pagesponse = new BasePageResponse(ConstantCode.SUCCESS);
        Map<String, Object> param = new HashMap<>();
        Integer start = Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);
        param.put("groupId",groupId);
        param.put("from",address);
        param.put("start",start);
        param.put("userParam",userParam);
        param.put("pageSize",pageSize);
        Integer count = chainUserService.queryTransactionCount(param);
        List<Transaction> Transactions = chainUserService.queryTransactionList(param);
        List<RspGetTransaction> rep = new ArrayList<>();
        for (Transaction transaction : Transactions) {
            RspGetTransaction rspEntity = new RspGetTransaction();
            rspEntity.setTransHash(transaction.getTransHash());
            rspEntity.setBlockHash(transaction.getBlockHash());
            rspEntity.setBlockNumber(transaction.getBlockNumber());
            rspEntity.setBlockTimesStr(DateTimeUtils.timestamp2String(
                    transaction.getBlockTime(), Constants.DEFAULT_DATA_TIME_FORMAT));
            rspEntity.setFrom(userService.queryNameByAddress(groupId,
                    transaction.getTransFrom()));
            rspEntity.setTo(transaction.getTransTo());
            rspEntity.setTransIndex(
                    CommonUtils.parseHexStr2Int(transaction.getTransIndex()));
            rep.add(rspEntity);
        }
        pagesponse.setData(rep);
        pagesponse.setTotalCount(count);
        log.info("end rep useTime:{} result:{}",
                Duration.between(startTime, Instant.now()).toMillis(),
                JsonTools.toJSONString(pagesponse));
        return pagesponse;
    }
}
