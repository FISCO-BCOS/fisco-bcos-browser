package org.bcos.browser.entity.dto;

import java.util.List;
import lombok.Data;

@Data
public class ConsensusFromChain {
    private List<Peer> peers;
}
