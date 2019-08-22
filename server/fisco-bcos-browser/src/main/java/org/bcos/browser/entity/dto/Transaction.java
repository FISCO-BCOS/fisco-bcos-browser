package org.bcos.browser.entity.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class Transaction {
    private String transHash;
    private int groupId;
    private String blockHash;
    private int blockNumber;
    private Timestamp blockTime;
    private Date blockDate;
    private String transFrom;
    private String transTo;
    private int transIndex;
    private String method;
}
