package org.bcos.browser.entity.dto;

import java.util.Date;
import lombok.Data;

@Data
public class TransactionByDay {
    private Date pkDate;
    private int groupId;
    private int txn;
}
