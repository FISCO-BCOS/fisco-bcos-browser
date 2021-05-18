package org.bcos.browser.mapper;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.ChainContract;
import org.bcos.browser.entity.dto.LatestTransCount;
import org.bcos.browser.entity.dto.Transaction;
import org.bcos.browser.entity.rsp.RsqChainUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransactionMapper {

    int getAllTransactionCount(Map<String, Object> map);

    List<Transaction> getTbTransactionByPage(Map<String, Object> map);

    List<Transaction> getTransFormByGroup(@Param(value = "groupId") int groupId);

    List<Transaction> getTbTransactionByGroup(@Param(value = "groupId") int groupId);

    List<LatestTransCount> queryLatestTransCount(@Param(value = "groupId") int groupId);

    int queryCountByFromOrTo(Map<String, Object> param);

    List<Transaction> queryTransactionListFromOrTo(Map<String, Object> map);


    void deletePartTxn(@Param(value = "groupId") int groupId,
            @Param(value = "subTransNum") int subTransNum);
}
