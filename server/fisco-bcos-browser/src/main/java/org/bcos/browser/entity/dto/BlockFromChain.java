package org.bcos.browser.entity.dto;

import java.util.List;
import lombok.Data;

@Data
public class BlockFromChain {
    private String hash;
    private String number;
    private String sealer;
    private List<String> sealerList;
    private String timestamp;
    private List<TransactionFromChain> transactions;
}
