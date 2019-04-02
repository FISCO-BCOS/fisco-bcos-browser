package org.bcos.browser.mapper;

import java.util.List;
import java.util.Map;
import org.bcos.browser.entity.dto.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionMapper {

    void add(Transaction transaction);
    
    void updateMethod(Transaction transaction);

    int getAllTransactionCount(Map<String, Object> map);

    List<Transaction> getTbTransactionByPage(Map<String, Object> map);
}
