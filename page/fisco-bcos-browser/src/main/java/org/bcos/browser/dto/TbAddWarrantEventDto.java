package org.bcos.browser.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TbAddWarrantEventDto implements Serializable{
    private static final long serialVersionUID = 1466810802015679544L;
    private int blockNumber;
    private String blockHash;
    private long transactionIndex;
    private String transactionHash;
    private String WarrantTokenAddress;
    private String fromAddress;
    private String fromName;
    private String fromID;
    private String toAddress;
    private String toName;
    private String toID;
    private String warrantID;
    private String warrantDetail;
    private Timestamp blockTimestamp;
    public int getBlockNumber() {
        return blockNumber;
    }
    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }
    public String getBlockHash() {
        return blockHash;
    }
    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
    public long getTransactionIndex() {
        return transactionIndex;
    }
    public void setTransactionIndex(long transactionIndex) {
        this.transactionIndex = transactionIndex;
    }
    public String getTransactionHash() {
        return transactionHash;
    }
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
    public String getWarrantTokenAddress() {
        return WarrantTokenAddress;
    }
    public void setWarrantTokenAddress(String warrantTokenAddress) {
        WarrantTokenAddress = warrantTokenAddress;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getFromName() {
        return fromName;
    }
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
    public String getFromID() {
        return fromID;
    }
    public void setFromID(String fromID) {
        this.fromID = fromID;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getToName() {
        return toName;
    }
    public void setToName(String toName) {
        this.toName = toName;
    }
    public String getToID() {
        return toID;
    }
    public void setToID(String toID) {
        this.toID = toID;
    }
    public String getWarrantID() {
        return warrantID;
    }
    public void setWarrantID(String warrantID) {
        this.warrantID = warrantID;
    }
    public String getWarrantDetail() {
        return warrantDetail;
    }
    public void setWarrantDetail(String warrantDtail) {
        this.warrantDetail = warrantDtail;
    }
    public Timestamp getBlockTimestamp() {
        return blockTimestamp;
    }
    public void setBlockTimestamp(Timestamp blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }
    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"blockNumber\":\"")
                .append(blockNumber).append('\"');
        sb.append(",\"blockHash\":\"")
                .append(blockHash).append('\"');
        sb.append(",\"transactionIndex\":\"")
                .append(transactionIndex).append('\"');
        sb.append(",\"transactionHash\":\"")
                .append(transactionHash).append('\"');
        sb.append(",\"WarrantTokenAddress\":\"")
                .append(WarrantTokenAddress).append('\"');
        sb.append(",\"fromAddress\":\"")
                .append(fromAddress).append('\"');
        sb.append(",\"fromName\":\"")
                .append(fromName).append('\"');
        sb.append(",\"fromID\":\"")
                .append(fromID).append('\"');
        sb.append(",\"toAddress\":\"")
                .append(toAddress).append('\"');
        sb.append(",\"toName\":\"")
                .append(toName).append('\"');
        sb.append(",\"toID\":\"")
                .append(toID).append('\"');
        sb.append(",\"warrantID\":\"")
                .append(warrantID).append('\"');
        sb.append(",\"warrantDetail\":\"")
                .append(warrantDetail).append('\"');
        sb.append(",\"blockTimestamp\":\"")
                .append(blockTimestamp).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
