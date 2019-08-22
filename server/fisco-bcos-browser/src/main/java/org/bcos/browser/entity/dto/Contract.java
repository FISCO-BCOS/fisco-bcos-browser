package org.bcos.browser.entity.dto;

import lombok.Data;

@Data
public class Contract {
    private int contractId;
    private String contractName;
    private String contractSource;
    private int contractStatus;
    private String contractAbi;
    private String contractBin;
    private String contractAddress;
    private String contractDesc;
    private String errorInfo;
    private String contractPath;
}
