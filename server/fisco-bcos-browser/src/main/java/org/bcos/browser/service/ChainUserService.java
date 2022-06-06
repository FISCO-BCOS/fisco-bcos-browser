package org.bcos.browser.service;

import java.util.List;
import java.util.Map;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.dto.Transaction;
import org.bcos.browser.entity.dto.UserQueryParam;
import org.bcos.browser.entity.rsp.RsqChainUser;
import org.bcos.browser.mapper.ChainUserMapper;
import org.bcos.browser.mapper.TransactionMapper;
import org.bcos.browser.util.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

/**
 * services for user data.
 */
@Log4j2
@Service
public class ChainUserService {
    @Autowired
    private ChainUserMapper chainUserMapper;
    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * query count of chainUser.
     */
    public Integer countOfChainUser(UserQueryParam userQueryParam) throws BaseException {
        try {
            Integer count = chainUserMapper.queryChainUserCount(userQueryParam);
            return count;
        } catch (RuntimeException ex) {
            log.error("fail countOfUser userParam:{}", JsonTools.toJSONString(userQueryParam), ex);
            throw new BaseException(ConstantCode.DB_EXCEPTION);
        }
    }

    /**
     * query chainUser list by page.
     */
    public List<RsqChainUser> queryChainUserList(UserQueryParam userQueryParam) throws BaseException {
        log.debug("start queryChainUserList userParam:{}", JsonTools.toJSONString(userQueryParam));
        // query user list
        List<RsqChainUser> chainUserList = chainUserMapper.queryChainUserList(userQueryParam);
        log.debug("end queryChainUserList chainUserList:{}", JsonTools.toJSONString(chainUserList));
        return chainUserList;
    }

    /**
     * queryTxChainUserCount
     * @param map
     * @return
     * @throws BaseException
     */
    public Integer queryTransactionCount(Map<String, Object> map) throws BaseException {
        try {
            Integer userTsCount = transactionMapper.queryCountByFromOrTo(map);
            return userTsCount;
        } catch (RuntimeException ex) {
            log.error("fail countOfUser userParam:{}", JsonTools.toJSONString(map), ex);
            throw new BaseException(ConstantCode.DB_EXCEPTION);
        }
    }

    /**
     * queryTxChainUserList
     * @param map
     * @return
     * @throws BaseException
     */
    public List<Transaction> queryTransactionList(Map<String, Object> map) throws BaseException {
        log.debug("start queryTxChainUserList param:{}", JsonTools.toJSONString(map));
        // query user list
        List<Transaction> Transactions = transactionMapper.queryTransactionListFromOrTo(map);
        log.debug("end queryTxChainUserList chainUserList:{}", JsonTools.toJSONString(Transactions));
        return Transactions;
    }
}
