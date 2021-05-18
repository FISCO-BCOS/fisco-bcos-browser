package org.bcos.browser.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = Constants.CONSTANT_PREFIX)
public class Constants {

    public static final String CONSTANT_PREFIX = "constant";
    private boolean configAuth = true;
    private String cronBlockChainInfo = "0/10 * * * * ?";
    private String cronTxnByDay = "30 0/1 * * * ?";
    private String cronAyncNode = "0 0/1 * * * ?";
    private String cronIfNodeActive = "0/10 * * * * ?";
    private String cronDeleteTxn = "0 0 * * * ?";
    private String cronSyncChainUser = "0/10 * * * * ?";
    private String cronSyncChainContract = "0/10 * * * * ?";
    private int keepTxnCnt = 500000;

    public static final String RPC_BASE_URI = "http://%s:%s";
    public static final String GET_BLOCK_NUMBER = "getBlockNumber";
    public static final String GET_PBFT_VIEW = "getPbftView";
    public static final String GET_TOTAL_TRANSACTION_COUNT = "getTotalTransactionCount";
    public static final String GET_PENDING_TX_SIZE = "getPendingTxSize";
    public static final String GET_BLOCK_BY_NUMBER = "getBlockByNumber";
    public static final String GET_BLOCK_BY_HASH = "getBlockByHash";
    public static final String GET_TRANSACTION_BY_HASH = "getTransactionByHash";
    public static final String GET_TRANSACTION_RECEIPT = "getTransactionReceipt";
    public static final String GET_CODE = "getCode";
    public static final String GET_CONSENSUS_STATUS = "getConsensusStatus";
    public static final String GET_SYNC_STATUS = "getSyncStatus";
    public static final String GET_OBSERVER_LIST = "getObserverList";
    public static final String GET_GROUP_PEERS = "getGroupPeers";
    public static final String GET_GROUP_LIST = "getGroupList";
    public static final String GET_CLIENT_VERSION = "getClientVersion";

    public static final String DEFAULT_DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String DATE_FORMAT_YYMMDD = "yyyy-MM-dd";
}
