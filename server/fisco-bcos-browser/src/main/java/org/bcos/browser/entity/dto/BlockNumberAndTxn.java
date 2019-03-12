package org.bcos.browser.entity.dto;

import lombok.Data;

@Data
public class BlockNumberAndTxn {
    private int blockNumber = 0;
    private int txn = 0;
}
