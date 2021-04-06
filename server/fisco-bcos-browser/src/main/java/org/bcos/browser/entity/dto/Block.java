package org.bcos.browser.entity.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Block {
    private String blockHash;
    private int number;
    private String sealer;
    private String sealerList;
    private Timestamp blockTime;
    private int txn;
}
