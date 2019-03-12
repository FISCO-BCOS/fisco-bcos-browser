package org.bcos.browser.entity.dto;

import lombok.Data;

@Data
public class BlockChainInfo {
    private int groupId;
    private int latestNumber;
    private int txn;
    private int pendingTxn;
    private int pbftView;
}
