package org.bcos.browser.entity.rsp;

import java.util.List;
import lombok.Data;
import org.bcos.browser.entity.dto.TransactionAndReceipt;

@Data
public class RspAnalyzeData {
    private List<TransactionAndReceipt> data;
}
