package org.bcos.browser.entity.dto;

import java.util.List;
import lombok.Data;

@Data
public class SyncInfoFromChain {
    private int blockNumber;
    private String nodeId;
    private List<Peer> peers;
}
