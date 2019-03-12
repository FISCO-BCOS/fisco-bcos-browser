package org.bcos.browser.entity.dto;

import lombok.Data;

@Data
public class Peer {
    private int blockNumber;
    private int view;
    private String nodeId;
}
