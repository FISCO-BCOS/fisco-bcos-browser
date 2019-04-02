package org.bcos.browser.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.BlockChainInfo;
import org.bcos.browser.entity.dto.TransactionByDay;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockChainInfoMapper {

    void add(BlockChainInfo blockChainInfo);

    void addTxnByDay(@Param(value = "groupId") int groupId);

    BlockChainInfo getBlockChainInfo(@Param(value = "groupId") int groupId);

    List<TransactionByDay> getLastTbTxnByDay(@Param(value = "groupId") int groupId,
            @Param(value = "dateTimeBegin") String dateTimeBegin,
            @Param(value = "dateTimeEnd") String dateTimeEnd);

    int getBlockNumber(int groupId);
}
