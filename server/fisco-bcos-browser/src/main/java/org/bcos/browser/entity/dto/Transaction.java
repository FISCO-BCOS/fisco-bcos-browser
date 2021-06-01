package org.bcos.browser.entity.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Transaction {
    private String transHash;
    private String blockHash;
    private int blockNumber;
    private Timestamp blockTime;
    private String transFrom;
    private String transTo;
    private String transIndex;
    private String contractAddress;
}
