package cn.webank.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigInteger;


public class TbTransactionReceiptDto implements Serializable{
    private static final long serialVersionUID = 8517059351055803017L;
    private String pkHash;
    private String blockHash;
    private Integer blockNumber;
    private String contractAddress;
    private BigInteger transactionIndex;
    private BigInteger gasUsed;
    private BigInteger cumulativeGasUsed;
    private String detailInfo;
    private String logs;



    public String getPkHash() {
        return pkHash;
    }

    public void setPkHash(String pkHash) {
        this.pkHash = pkHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(BigInteger transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public BigInteger getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(BigInteger gasUsed) {
        this.gasUsed = gasUsed;
    }

    public BigInteger getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public void setCumulativeGasUsed(BigInteger cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkHash\":\"")
                .append(pkHash).append('\"');
        sb.append(",\"blockHash\":\"")
                .append(blockHash).append('\"');
        sb.append(",\"blockNumber\":")
                .append(blockNumber);
        sb.append(",\"contractAddress\":\"")
                .append(contractAddress).append('\"');
        sb.append(",\"transactionIndex\":")
                .append(transactionIndex);
        sb.append(",\"gasUsed\":")
                .append(gasUsed);
        sb.append(",\"cumulativeGasUsed\":")
                .append(cumulativeGasUsed);
        sb.append(",\"detailInfo\":\"")
                .append(detailInfo).append('\"');
        sb.append(",\"logs\":\"")
                .append(logs).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
