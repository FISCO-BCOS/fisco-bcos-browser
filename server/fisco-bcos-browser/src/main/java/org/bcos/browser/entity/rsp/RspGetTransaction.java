package org.bcos.browser.entity.rsp;

import lombok.Data;

@Data
public class RspGetTransaction {
    private String transHash;
    private String blockHash;
    private int blockNumber;
    private String blockTimesStr;
    private String from;
    private String to;
    private int transIndex;
}
