package org.bcos.browser.entity.rsp;

import lombok.Data;

@Data
public class RspGetTxnLatelyDays {
    private String dateStr;
    private int txn;
}
