package org.bcos.browser.entity.rsp;

import lombok.Data;

@Data
public class RspGetBlock {
    private String blockHash;
    private int number;
    private String sealer;
    private String dateTimeStr;
    private int txn;
}
