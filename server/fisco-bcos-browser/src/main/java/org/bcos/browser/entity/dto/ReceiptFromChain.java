package org.bcos.browser.entity.dto;

import java.util.List;
import lombok.Data;

@Data
public class ReceiptFromChain {
    private String output;
    private String blockHash;
    private String logsBloom;
    private String gasUsed;
    private String blockNumber;
    private String contractAddress;
    private String from;
    private String transactionIndex;
    private String to;
    private List<Object> logs;
    private String transactionHash;
    private String status;
}
