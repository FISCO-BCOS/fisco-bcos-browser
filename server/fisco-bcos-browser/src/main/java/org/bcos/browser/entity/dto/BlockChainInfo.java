package org.bcos.browser.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlockChainInfo {
    private int groupId;
    private int latestNumber;
    private int txn;
    private int pendingTxn;
    private int pbftView;
    private int nodeCount;

    public BlockChainInfo(int groupId) {
        this.groupId = groupId;
    }
}
