package org.bcos.browser.entity.dto;

import lombok.Data;

@Data
public class TransactionAndReceipt {
    private TransactionFromChain transactionFromChain;
    private ReceiptFromChain receiptFromChain;
}
