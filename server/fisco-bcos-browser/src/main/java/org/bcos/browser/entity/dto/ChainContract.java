package org.bcos.browser.entity.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChainContract {
    private int contractId;
    private int groupId;
    private int blockHeight;
    private String contractAddress;
    private String transFrom;
    private String transHash;
    private Timestamp blockTime;
    private String blockTimeStr;
}
