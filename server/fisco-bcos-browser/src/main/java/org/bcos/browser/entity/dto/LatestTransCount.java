package org.bcos.browser.entity.dto;

import java.util.Date;
import lombok.Data;

/**
 * result of latest transCount.
 */
@Data
public class LatestTransCount {
    private int txn;
    private int blockNumber;
    private Date pkDate;
}
