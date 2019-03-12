package org.bcos.browser.entity.dto;

import lombok.Data;

@Data
public class TransactionFromChain {
    private String blockHash;
    private String input;
    private String blockNumber;
    private String gas;
    private String from;
    private String transactionIndex;
    private String to;
    private String nonce;
    private String value;
    private String hash;
    private String gasPrice;
}
