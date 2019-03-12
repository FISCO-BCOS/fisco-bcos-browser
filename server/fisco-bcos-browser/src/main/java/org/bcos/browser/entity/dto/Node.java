package org.bcos.browser.entity.dto;

import lombok.Data;

@Data
public class Node {
    private String nodeId;
    private int groupId;
    private String ip;
    private String rpcPort;
    private String p2pPort;
    private int blockNumber;
    private int pbftView;
    private int status;
    private int type;
}
